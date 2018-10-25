package com.omvp.app.ui.samples.locale.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.locale.presenter.SampleLocalePresenterModule
import dagger.Binds
import dagger.Module

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleLocalePresenterModule::class))
abstract class SampleLocaleFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleLocaleFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleLocaleFragment): SampleLocaleView

}
