package com.omvp.app.base

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle

import com.omvp.app.injector.module.HelperModule
import com.omvp.app.injector.module.InterceptorActivityModule
import com.omvp.app.injector.module.UseCaseModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.util.DisposableManager
import com.omvp.app.util.OperationBroadcastManager

import dagger.Module
import dagger.Provides

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module(includes = arrayOf(
        InterceptorActivityModule::class,
        UseCaseModule::class,
        HelperModule::class
))
object BaseActivityModule {

    @JvmStatic
    @Provides
    @PerActivity
    internal fun resources(activity: Activity): Resources {
        return activity.resources
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun activityExtras(activity: Activity): Bundle {
        return if (activity.intent != null && activity.intent.extras != null) activity.intent.extras else Bundle()
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun activityDisposableManager(): DisposableManager {
        return DisposableManager()
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun operationBroadcastManager(activity: Activity): OperationBroadcastManager {
        return OperationBroadcastManager(activity)
    }

}
