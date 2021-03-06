package com.omvp.app.ui.samples.pager.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.pager.presenter.SamplePagerPresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SamplePagerFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SamplePagerPresenterModule::class))
abstract class SamplePagerFirstFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SamplePagerFragment
     * @return the fragment
     */

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun firstFragment(fragment: SamplePagerFirstFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun firstView(fragment: SamplePagerFirstFragment): SamplePagerView
}
