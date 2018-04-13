package com.omvp.app.ui.samples.multiple

import android.app.Activity

import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.multiple.bottom.view.SampleBottomFragment
import com.omvp.app.ui.samples.multiple.bottom.view.SampleBottomFragmentModule
import com.omvp.app.ui.samples.multiple.top.view.SampleTopFragment
import com.omvp.app.ui.samples.multiple.top.view.SampleTopFragmentModule

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides splash activity dependencies
 */
@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class SampleMultipleActivityModule {

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
    internal abstract fun activity(activity: SampleMultipleActivity): Activity

    /**
     * The main activity listens to the events in the [SampleTopFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun topFragmentCallback(activity: SampleMultipleActivity): SampleTopFragment.FragmentCallback

    /**
     * The main activity listens to the events in the [SampleTopFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun bottomFragmentCallback(activity: SampleMultipleActivity): SampleBottomFragment.FragmentCallback

    // =============================================================================================

    /**
     * Provides the injector for the [SampleTopFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SampleTopFragmentModule::class))
    internal abstract fun topFragmentInjector(): SampleTopFragment

    /**
     * Provides the injector for the [SampleBottomFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SampleBottomFragmentModule::class))
    internal abstract fun bottonFragmentInjector(): SampleBottomFragment
}
