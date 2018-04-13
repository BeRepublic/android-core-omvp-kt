package com.omvp.app.base

import com.omvp.app.injector.scope.PerService
import com.omvp.app.util.DisposableManager

import javax.inject.Named

import dagger.Module
import dagger.Provides

/**
 * Provides base service dependencies. This must be included in all services modules, which must
 * provide a concrete implementation of [android.app.Service].
 */
@Module
object BaseServiceModule {

    internal const val DISPOSABLE_SERVICE_MANAGER = "BaseServiceModule.disposableServiceManager"

    @JvmStatic
    @Provides
    @Named(DISPOSABLE_SERVICE_MANAGER)
    @PerService
    internal fun disposableServiceManager(): DisposableManager {
        return DisposableManager()
    }

}
