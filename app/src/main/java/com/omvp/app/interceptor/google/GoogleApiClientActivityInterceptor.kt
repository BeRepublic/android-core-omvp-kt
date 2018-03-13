package com.omvp.app.interceptor.google

import android.app.Activity
import android.os.Bundle

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.raxdenstudios.square.interceptor.ActivityInterceptor

class GoogleApiClientActivityInterceptor(activity: Activity, private val mGoogleSignInOptions: GoogleSignInOptions, callback: GoogleApiClientInterceptorCallback) :
        ActivityInterceptor<GoogleApiClientInterceptorCallback>(activity, callback),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClientInterceptor {

    private lateinit var mGoogleApiClient: GoogleApiClient

    init {
        mActivity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

    override fun onStart() {
        if (!mGoogleApiClient.isConnected && !mGoogleApiClient.isConnecting) {
            mGoogleApiClient.connect()
        }
        super.onStart()
    }

    override fun onStop() {
        if (mGoogleApiClient.isConnected || mGoogleApiClient.isConnecting) {
            mGoogleApiClient.disconnect()
        }
        super.onStop()
    }

    override fun onConnected(bundle: Bundle?) {
        mCallback.onGoogleApiClientConnected(bundle, mGoogleApiClient)
    }

    override fun onConnectionSuspended(i: Int) {
        mCallback.onGoogleApiClientConnectionSuspended(i)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        mCallback.onGoogleApiClientConnectionFailed(connectionResult)
    }

}
