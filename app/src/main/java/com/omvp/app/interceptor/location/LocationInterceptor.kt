package com.omvp.app.interceptor.location

import android.location.Location

import com.google.android.gms.common.api.GoogleApiClient
import com.raxdenstudios.square.interceptor.Interceptor

interface LocationInterceptor : Interceptor {

    fun lastLocation(): Location?

    fun setGoogleApiClient(googleApiClient: GoogleApiClient)

    fun requestLocationUpdates()

}
