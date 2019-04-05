package com.omvp.app.interceptor.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.omvp.app.R
import com.raxdenstudios.commons.util.Utils
import com.raxdenstudios.square.interceptor.ActivitySimpleInterceptor
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class LocationActivityInterceptor(
        activity: FragmentActivity,
        locationRequest: LocationRequest
) : ActivitySimpleInterceptor(activity), LocationInterceptor {

    companion object {
        private const val KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates"
        private const val KEY_REQUESTING_LOCATION_PERMISSIONS = "requesting-permissions"
        private const val KEY_LOCATION = "location"

        /**
         * Constant used in the location settings dialog.
         */
        private const val REQUEST_CHECK_SETTINGS = 0x1
    }

    /**
     * Provides access to the Fused Location Provider API.
     */
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    /**
     * Provides access to the Location Settings API.
     */
    private lateinit var mSettingsClient: SettingsClient
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private val mLocationRequest: LocationRequest = locationRequest
    /**
     * Callback for Location events.
     */
    private lateinit var mLocationCallback: LocationCallback
    /**
     * Represents a geographical location.
     */
    private var mCurrentLocation: Location? = null
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private var mRequestingLocationUpdates: Boolean = false
    private var mRequestingLocationPermissions: Boolean = false
    private var mLocationListenerList: MutableList<LocationListener>? = null
    private var mRxPermissions: RxPermissions = RxPermissions(activity)

    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val o = Any()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
        // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    Timber.i("User agreed to make required location settings changes.")
                    mRequestingLocationUpdates = false
                }
                Activity.RESULT_CANCELED -> {
                    Timber.i("User choose not to make required location settings changes.")
                    mRequestingLocationUpdates = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRequestingLocationUpdates = false
        mRequestingLocationPermissions = false

        restoreDataFromBundle(savedInstanceState)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mSettingsClient = LocationServices.getSettingsClient(activity)

        createLocationCallback()
        buildLocationSettingsRequest()
    }

    override fun onResume() {
        super.onResume()
        restoreLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLocationListenerList != null) {
            mLocationListenerList!!.clear()
            mLocationListenerList = null
        }
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        storeDataToBundle(outState!!)
        super.onSaveInstanceState(outState)
    }

    // =============================================================================================

    override fun addLocationListener(locationListener: LocationListener) {
        if (mLocationListenerList == null) {
            mLocationListenerList = ArrayList()
        }
        mLocationListenerList!!.add(locationListener)
    }

    override fun getCurrentLocation(): Location? {
        return mCurrentLocation
    }

    // =============================================================================================

    private fun storeDataToBundle(outState: Bundle) {
        outState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates)
        outState.putBoolean(KEY_REQUESTING_LOCATION_PERMISSIONS, mRequestingLocationPermissions)
        outState.putParcelable(KEY_LOCATION, mCurrentLocation)
    }

    private fun restoreDataFromBundle(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_UPDATES)
            }
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_PERMISSIONS)) {
                mRequestingLocationPermissions = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_PERMISSIONS)
            }
            // Update the value of mCurrentLocation from the Bundle and update the UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            }
        }
    }

    /**
     * Creates a callback for receiving location events.
     */
    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                mCurrentLocation = locationResult?.lastLocation
                onLocationChanged(mCurrentLocation)
            }
        }
    }

    /**
     * Uses a [com.google.android.gms.location.LocationSettingsRequest.Builder] to build
     * a [com.google.android.gms.location.LocationSettingsRequest] that is used for checking
     * if a device has the needed location settings.
     */
    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    private fun onLocationChanged(location: Location?) {
        if (mLocationListenerList != null) {
            for (locationListener in mLocationListenerList!!) {
                locationListener.onLocationChanged(location)
            }
        }
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        mRequestingLocationUpdates = true
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(activity) {
                    Timber.d("All location settings are satisfied.")
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                    if (mCurrentLocation != null) {
                        onLocationChanged(mCurrentLocation)
                    }
                }
                .addOnFailureListener(activity) { e ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Timber.i("Location settings are not satisfied. Attempting to upgrade location settings ")
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                                Timber.i("PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> showDialog(AlertDialog.Builder(activity)
                                .setTitle(R.string.location_permission_settings_title)
                                .setMessage(R.string.location_permission_settings_description)
                                .setPositiveButton(R.string.location_permission_settings_positive_button) { _, _ ->
                                    mRequestingLocationUpdates = false
                                    launchSettings()
                                }
                                .setNegativeButton(R.string.location_permission_settings_negative_button) { _, _ -> finishActivity() }
                                .setCancelable(false)
                                .create())
                    }
                    if (mCurrentLocation != null) {
                        onLocationChanged(mCurrentLocation)
                    }
                }
    }

    private fun restoreLocationUpdates() {
        synchronized(o) {
            Timber.d("restoreLocationUpdates")
            if (!mRequestingLocationUpdates && checkPermissions()) {
                requestLocationUpdates()
            } else if (!checkPermissions()) {
                requestPermissions()
            }
        }
    }

    private fun stopLocationUpdates() {
        synchronized(o) {
            if (mRequestingLocationUpdates && checkPermissions()) {
                removeLocationUpdates()
            }
        }
    }

    private fun removeLocationUpdates() {
        Timber.d("removeLocationUpdates")
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(activity) { mRequestingLocationUpdates = false }
    }

    private fun checkPermissions(): Boolean {
        return mRxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestPermissions() {
        if (!mRequestingLocationPermissions) {
            mRequestingLocationPermissions = true
            mCompositeDisposable.add(mRxPermissions.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { shouldShowRequestPermissionRationale ->
                        if (shouldShowRequestPermissionRationale!!) {
                            showDialog(AlertDialog.Builder(activity)
                                    .setTitle(R.string.location_permission_title)
                                    .setMessage(R.string.location_permission_description)
                                    .setPositiveButton(R.string.location_permission_positive_button) { _, _ -> requestLocationPermissions() }
                                    .setNegativeButton(R.string.location_permission_negative_button) { _, _ -> finishActivity() }
                                    .setCancelable(false)
                                    .create())
                        } else {
                            requestLocationPermissions()
                        }
                    })
        }
    }

    private var mCurrentDialog: AlertDialog? = null

    private fun showDialog(dialog: AlertDialog) {
        if (mCurrentDialog != null) {
            mCurrentDialog!!.dismiss()
            mCurrentDialog = null
        }
        mCurrentDialog = dialog
        mCurrentDialog!!.show()
    }

    private fun requestLocationPermissions() {
        mCompositeDisposable.add(mRxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Permission>() {
                    override fun onNext(permission: Permission) {
                        if (permission.granted) {
                            requestLocationUpdates()
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            showDialog(AlertDialog.Builder(activity)
                                    .setTitle(R.string.location_permission_title)
                                    .setMessage(R.string.location_permission_description)
                                    .setPositiveButton(R.string.location_permission_positive_button) { _, _ -> requestLocationPermissions() }
                                    .setNegativeButton(R.string.location_permission_negative_button) { _, _ -> finishActivity() }
                                    .setCancelable(false)
                                    .create())
                        } else {
                            showDialog(AlertDialog.Builder(activity)
                                    .setTitle(R.string.location_permission_force_title)
                                    .setMessage(R.string.location_permission_force_description)
                                    .setPositiveButton(R.string.location_permission_force_positive_button) { _, _ -> launchSettings() }
                                    .setNegativeButton(R.string.location_permission_force_negative_button) { _, _ -> finishActivity() }
                                    .setCancelable(false)
                                    .create())
                        }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                    }

                    override fun onComplete() {
                        mRequestingLocationPermissions = false
                    }
                }))
    }

    private fun launchSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", Utils.getPackageName(activity), null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    private fun finishActivity() {
        activity.finish()
    }

}
