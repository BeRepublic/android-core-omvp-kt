package com.omvp.app.base.mvp

import android.app.Activity
import android.app.FragmentManager

import com.omvp.app.base.BaseActivityModule
import com.omvp.app.injector.scope.PerActivity

import dagger.Module
import dagger.Provides

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module(includes = arrayOf(BaseActivityModule::class))
object BaseFragmentActivityModule {

    @JvmStatic
    @Provides
    @PerActivity
    internal fun activityFragmentManager(activity: Activity): FragmentManager {
        return activity.fragmentManager
    }

}
