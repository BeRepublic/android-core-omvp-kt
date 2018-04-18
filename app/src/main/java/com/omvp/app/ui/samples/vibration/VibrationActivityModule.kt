package com.omvp.app.ui.samples.vibration

import android.app.Activity

import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.vibration.view.VibrationFragment
import com.omvp.app.ui.samples.vibration.view.VibrationFragmentModule
import com.omvp.app.ui.splash.view.SplashFragment

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides splash activity dependencies
 */
@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class VibrationActivityModule {

    /**
     * As per the contract specified in [BaseActivityModule]; "This must be included in all
     * activity modules, which must provide a concrete implementation of [Activity]."
     *
     *
     * This provides the activity required to inject the dependencies into the
     * [BaseActivity].
     *
     * @param activity the SplashActivity
     * @return the activity
     */
    @Binds
    @PerActivity
    internal abstract fun activity(activity: VibrationActivity): Activity

    /**
     * The main activity listens to the events in the [VibrationFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun fragmentCallback(activity: VibrationActivity): VibrationFragment.FragmentCallback

    // =============================================================================================

    /**
     * Provides the injector for the [SplashFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(VibrationFragmentModule::class))
    internal abstract fun fragmentInjector(): VibrationFragment

}
