package com.omvp.app.injector.module

import com.google.android.gms.location.LocationRequest
import com.omvp.commons.Constants

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    @Singleton
    internal fun provideHighLocationRequest(): LocationRequest {
        return LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                //                .setExpirationDuration(TimeUnit.SECONDS.toMillis(Constants.LOCATION_TIMEOUT_IN_SECONDS))
                //                .setNumUpdates(Constants.LOCATION_UPDATES)
                .setInterval(Constants.LOCATION_INTERVAL.toLong())
    }

}
