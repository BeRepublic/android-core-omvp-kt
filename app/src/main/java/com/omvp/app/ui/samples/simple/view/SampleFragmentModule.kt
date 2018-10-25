package com.omvp.app.ui.samples.simple.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.ktsamples.ktsample.presenter.SamplePresenterModule
import com.omvp.app.ui.ktsamples.ktsample.view.SampleFragment
import com.omvp.app.ui.ktsamples.ktsample.view.SampleView
import dagger.Binds
import dagger.Module

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SamplePresenterModule::class))
abstract class SampleFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleFragment): SampleView

}
