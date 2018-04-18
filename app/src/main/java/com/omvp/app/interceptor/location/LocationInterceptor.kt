package com.omvp.app.interceptor.location

import android.location.Location
import com.google.android.gms.location.LocationListener
import com.raxdenstudios.square.interceptor.Interceptor

interface LocationInterceptor : Interceptor {

    fun getCurrentLocation(): Location?

    fun addLocationListener(locationListener: LocationListener)


}
