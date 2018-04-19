package com.omvp.app.ui.samples.requestphone

import android.app.Activity
import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.requestphone.view.SampleRequestPhoneFragment
import com.omvp.app.ui.samples.requestphone.view.SampleRequestPhoneFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class SampleRequestPhoneActivityModule {
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
    internal abstract fun activity(activity: SampleRequestPhoneActivity): Activity

    // =============================================================================================

    /**
     * The main activity listens to the events in the [SampleRequestPhoneFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun fragmentCallback(activity: SampleRequestPhoneActivity): SampleRequestPhoneFragment.FragmentCallback

    /**
     * Provides the injector for the [SampleRequestPhoneFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SampleRequestPhoneFragmentModule::class))
    internal abstract fun sampleRequestPhoneFragment(): SampleRequestPhoneFragment
}
