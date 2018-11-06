package com.omvp.app.ui.samples.inputs.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.inputs.presenter.SampleInputPresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleInputPresenterModule::class))
abstract class SampleInputFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleInputFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleInputFragment): SampleInputView

}
