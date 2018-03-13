package com.omvp.app.interceptor.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.widget.Toast

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.omvp.app.helper.DialogHelper
import com.omvp.app.util.PermissionUtil
import com.raxdenstudios.commons.util.Utils
import com.raxdenstudios.square.interceptor.ActivityInterceptor

import java.util.concurrent.CountDownLatch

import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LocationActivityInterceptor(activity: Activity, private val mLocationRequest: LocationRequest, callback: LocationInterceptorCallback) :
        ActivityInterceptor<LocationInterceptorCallback>(activity, callback),
        LocationListener,
        LocationInterceptor {

    private val mDialogHelper: DialogHelper = DialogHelper(mActivity, activity.fragmentManager)
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mRequestingLocationUpdates: Boolean = false
    private var mRequestingLocationUpdatesWorking: Boolean = false
    private var mDisposable: Disposable? = null
    private var mCountDownLatch: CountDownLatch = CountDownLatch(1)
    private val o = Any()
    private var mLocation: Location? = null

    private val isGoogleApiClientPrepared: Boolean
        get() = mGoogleApiClient != null && mGoogleApiClient!!.isConnected

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        restoreLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")

        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")

        if (mDisposable != null) {
            mDisposable!!.dispose()
            mDisposable = null
        }
    }

    override fun setGoogleApiClient(googleApiClient: GoogleApiClient) {
        Timber.d("setGoogleApiClient")
        mGoogleApiClient = googleApiClient
        mCountDownLatch.countDown()
    }

    override fun requestLocationUpdates() {
        mDisposable = Completable
                .create { emitter ->
                    try {
                        if (!emitter.isDisposed) {
                            Timber.d("requestLocationUpdates await...")
                            if (isGoogleApiClientPrepared) {
                                mCountDownLatch.countDown()
                            }
                            mCountDownLatch.await()
                            Timber.d("requestLocationUpdates restored!")
                            emitter.onComplete()
                        }
                    } catch (ex: Exception) {
                        emitter.onError(ex)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onStart() {
                        Timber.d("requestLocationUpdates onStart!")
                        mRequestingLocationUpdates = true
                    }

                    override fun onComplete() {
                        Timber.d("requestLocationUpdates onComplete!")
                        performLocationUpdates()
                        mRequestingLocationUpdates = false
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Timber.d("requestLocationUpdates onError!")
                        mRequestingLocationUpdates = false
                        Timber.e(e)
                    }
                })
    }

    override fun lastLocation(): Location? {
        return mLocation
    }

    override fun onLocationChanged(location: Location) {
        Timber.d("onLocationChanged " + location.toString())
        mLocation = location
        mCallback.onLocationChanged(location)
    }

    @SuppressLint("MissingPermission")
    private fun performLocationUpdates() {
        synchronized(o) {
            Timber.d("performLocationUpdates trying to perform...")
            if (!isGoogleApiClientPrepared) {
                Timber.d("performLocationUpdates - Failed to retrieveUser location updates, GoogleApiClient is not prepared.")
                onLocationError(GOOGLE_API_CLIENT_FAILED)
            } else if (!hasLocationPermission()) {
                Timber.d("performLocationUpdates - Failed to retrieveUser location updates, missing location permission.")
                onLocationError(LOCATE_PERMISSION_FAILED)
            } else if (!hasGPSProviderEnabled()) {
                Timber.d("performLocationUpdates - Failed to retrieveUser location updates, missing location permission.")
                onLocationError(LOCATE_PROVIDER_FAILED)
            } else {
                Timber.d("performLocationUpdates SUCCESS!")
                mRequestingLocationUpdatesWorking = true
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
                val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                if (location != null) {
                    onLocationChanged(location)
                } else {
                    onLocationError(-1)
                }
            }
        }
    }

    private fun restoreLocationUpdates() {
        synchronized(o) {
            Timber.d("restoreLocationUpdates")
            if (!mRequestingLocationUpdates && !mRequestingLocationUpdatesWorking) {
                requestLocationUpdates()
            }
        }
    }

    private fun stopLocationUpdates() {
        synchronized(o) {
            Timber.d("stopLocationUpdates")
            if (isGoogleApiClientPrepared) {
                removeLocationUpdates()
            }
        }
    }

    private fun removeLocationUpdates() {
        synchronized(o) {
            Timber.d("removeLocationUpdates")
            mRequestingLocationUpdates = false
            mRequestingLocationUpdatesWorking = false
            mCountDownLatch = CountDownLatch(1)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }

    private fun hasGPSProviderEnabled(): Boolean {
        return if (Utils.hasGPSProviderEnabled(mContext)) {
            Timber.d("hasGPSProviderEnabled gps enable...]")
            true
        } else {
            Timber.d("hasGPSProviderEnabled gps disabled...")
            //TODO SHOW DIALOG
            Toast.makeText(mContext, "Actualmente tienes el GPS desactivado, activalo para poder obtener resultados de calidad", Toast.LENGTH_LONG).show()

            false
        }
    }

    private fun onLocationError(status: Int) {
        mCallback.onLocationError(status)
    }

    private fun hasLocationPermission(): Boolean {
        val hasLocationPermission = !PermissionUtil.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        Timber.d("hasLocationPermission $hasLocationPermission")
        return hasLocationPermission
    }

    companion object {

        const val LOCATE_PERMISSION_FAILED = 1
        const val GOOGLE_API_CLIENT_FAILED = 2
        const val LOCATE_PROVIDER_FAILED = 3
    }
}
