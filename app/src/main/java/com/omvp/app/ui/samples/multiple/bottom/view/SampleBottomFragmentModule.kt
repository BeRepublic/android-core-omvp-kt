package com.omvp.app.ui.samples.multiple.bottom.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.multiple.bottom.presenter.SampleBottomPresenterModule
import dagger.Binds
import dagger.Module

/**
 * Provides SampleBottomFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleBottomPresenterModule::class))
abstract class SampleBottomFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleBottomFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleBottomFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleBottomFragment): SampleBottomView

}
