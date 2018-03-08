package com.omvp.app.ui.samples.samplepager

import android.app.Activity

import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerFirstFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerFirstFragmentModule
import com.omvp.app.ui.samples.samplepager.view.SamplePagerSecondFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerSecondFragmentModule

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides splash activity dependencies
 */
@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class SamplePagerActivityModule {

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
    internal abstract fun activity(activity: SamplePagerActivity): Activity

    // =============================================================================================

    /**
     * The main activity listens to the events in the [SamplePagerFirstFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun firstFragmentCallback(activity: SamplePagerActivity): SamplePagerFirstFragment.SamplePagerFirstFragmentCallback

    /**
     * The main activity listens to the events in the [SamplePagerSecondFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun secondFragmentCallback(activity: SamplePagerActivity): SamplePagerSecondFragment.SamplePagerSecondFragmentCallback

    /**
     * Provides the injector for the [SamplePagerFirstFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SamplePagerFirstFragmentModule::class))
    internal abstract fun firstFragmentInjector(): SamplePagerFirstFragment

    /**
     * Provides the injector for the [SamplePagerSecondFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SamplePagerSecondFragmentModule::class))
    internal abstract fun secondFragmentInjector(): SamplePagerSecondFragment
}
