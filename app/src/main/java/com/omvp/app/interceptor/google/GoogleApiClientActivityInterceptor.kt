package com.omvp.app.interceptor.google

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.raxdenstudios.square.interceptor.ActivityInterceptor

class GoogleApiClientActivityInterceptor(activity: FragmentActivity, private val mGoogleSignInOptions: GoogleSignInOptions, callback: GoogleApiClientInterceptorCallback) :
        ActivityInterceptor<GoogleApiClientInterceptorCallback>(activity, callback),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClientInterceptor {

    private lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(activity)
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
        callback?.onGoogleApiClientConnected(bundle, mGoogleApiClient)
    }

    override fun onConnectionSuspended(i: Int) {
        callback?.onGoogleApiClientConnectionSuspended(i)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        callback?.onGoogleApiClientConnectionFailed(connectionResult)
    }

}
