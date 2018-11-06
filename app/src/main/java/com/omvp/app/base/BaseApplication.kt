package com.omvp.app.base

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.omvp.app.injector.component.DaggerApplicationComponent
import com.raxdenstudios.commons.util.SDKUtils
import dagger.android.*
import javax.inject.Inject

/**
 * The Android [MultiDexApplication].
 */
abstract class BaseApplication : MultiDexApplication(),
        HasActivityInjector,
        HasServiceInjector,
        HasBroadcastReceiverInjector {

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var mServiceInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var mBroadcastReceiverDispatchingAndroidInjector: DispatchingAndroidInjector<BroadcastReceiver>

    // =============== LifeCycle ===================================================================

    override fun onCreate() {
        super.onCreate()

        initCompatVector()
        initDaggerApplicationComponent()
    }

    // =============== HasActivityInjector =========================================================

    override fun activityInjector(): AndroidInjector<Activity>? = activityInjector

    override fun serviceInjector(): AndroidInjector<Service>? = mServiceInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver>? = mBroadcastReceiverDispatchingAndroidInjector

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
