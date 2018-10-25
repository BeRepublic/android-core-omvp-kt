package com.omvp.app.ui.samples.requestphone.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterModule
import dagger.Binds
import dagger.Module


/**
 * Provides SampleRequestPhoneFlowFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleRequestPhonePresenterModule::class))
abstract class SampleRequestPhoneFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleRequestPhoneFlowFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleRequestPhoneFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleRequestPhoneFragment): SampleRequestPhoneView
}
