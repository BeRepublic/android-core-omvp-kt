package com.omvp.app.service

import android.app.Service

import com.omvp.app.base.BaseService
import com.omvp.app.base.BaseServiceModule
import com.omvp.app.injector.scope.PerService

import dagger.Binds
import dagger.Module

@Module(includes = arrayOf(BaseServiceModule::class))
abstract class AppFirebaseMessagingServiceModule {

    /**
     * As per the contract specified in [BaseServiceModule]; "This must be included in all
     * services modules, which must provide a concrete implementation of [Service]."
     *
     *
     * This provides the service required to inject the dependencies into the
     * [BaseService].
     *
     * @param service the AppFirebaseMessagingService
     * @return the service
     */
    @Binds
    @PerService
    internal abstract fun service(service: AppFirebaseMessagingService): Service

}
