package com.omvp.app.ui.samples.samplemultiple.top.view

import android.app.Fragment

import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.samplemultiple.top.presenter.SampleTopPresenterModule
import com.omvp.app.ui.samples.samplemultiple.top.view.SampleTopView

import dagger.Binds
import dagger.Module

/**
 * Provides SampleTopFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleTopPresenterModule::class))
abstract class SampleTopFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleTopFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleTopFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleTopFragment): SampleTopView

}
