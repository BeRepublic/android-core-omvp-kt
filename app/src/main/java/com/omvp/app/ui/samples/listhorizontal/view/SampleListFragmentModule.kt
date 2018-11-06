package com.omvp.app.ui.samples.listhorizontal.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.listhorizontal.presenter.SampleListHorizontalPresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SampleListHorizontalFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleListHorizontalPresenterModule::class))
abstract class SampleListFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleListHorizontalFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleListHorizontalFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleListHorizontalFragment): SampleListHorizontalView

}
