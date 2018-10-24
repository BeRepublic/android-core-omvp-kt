package com.omvp.app.base

import android.app.Activity
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.omvp.app.injector.component.DaggerApplicationComponent
import com.raxdenstudios.commons.util.SDKUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * The Android [MultiDexApplication].
 */
abstract class BaseApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    // =============== LifeCycle ===================================================================

    override fun onCreate() {
        super.onCreate()

        initCompatVector()
        initDaggerApplicationComponent()
    }

    // =============== HasActivityInjector =========================================================

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityInjector
    }

    // =============== Support methods =============================================================

    private fun initCompatVector() {
        if (!SDKUtils.hasLollipop()) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private fun initDaggerApplicationComponent() {
        DaggerApplicationComponent.builder().create(this).inject(this)
    }

}
