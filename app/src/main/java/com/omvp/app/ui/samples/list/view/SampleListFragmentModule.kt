package com.omvp.app.ui.samples.list.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.list.presenter.SampleListPresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SampleListFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleListPresenterModule::class))
abstract class SampleListFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleListFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleListFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleListFragment): SampleListView

}
