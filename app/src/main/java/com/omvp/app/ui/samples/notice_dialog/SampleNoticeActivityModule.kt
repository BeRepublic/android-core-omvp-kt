package com.omvp.app.ui.samples.notice_dialog


import android.app.Activity

import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.notice_dialog.view.SampleNoticeFragment
import com.omvp.app.ui.samples.notice_dialog.view.SampleNoticeFragmentModule

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides notice dialog activity dependencies
 */
@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class SampleNoticeActivityModule {

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
    internal abstract fun activity(activity: SampleNoticeActivity): Activity

    /**
     * The main activity listens to the events in the [SampleNoticeFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun fragmentCallback(activity: SampleNoticeActivity): SampleNoticeFragment.FragmentCallback

    // =============================================================================================

    /**
     * Provides the injector for the [SampleNoticeFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SampleNoticeFragmentModule::class))
    internal abstract fun fragmentInjector(): SampleNoticeFragment

}
