package com.omvp.app.interceptor.location

import android.location.Location

import com.raxdenstudios.square.interceptor.InterceptorCallback

interface LocationInterceptorCallback : InterceptorCallback {

    fun onLocationError(status: Int)

    fun onLocationChanged(location: Location)
}
