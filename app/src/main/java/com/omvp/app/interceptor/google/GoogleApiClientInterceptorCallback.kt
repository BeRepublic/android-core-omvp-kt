package com.omvp.app.interceptor.google

import android.os.Bundle

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.raxdenstudios.square.interceptor.InterceptorCallback

interface GoogleApiClientInterceptorCallback : InterceptorCallback {

    fun onGoogleApiClientConnected(bundle: Bundle?, googleApiClient: GoogleApiClient)

    fun onGoogleApiClientConnectionSuspended(i: Int)

    fun onGoogleApiClientConnectionFailed(connectionResult: ConnectionResult)
}
