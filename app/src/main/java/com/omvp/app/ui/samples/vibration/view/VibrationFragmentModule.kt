package com.omvp.app.ui.samples.vibration.view

import android.support.v4.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.vibration.presenter.VibrationPresenterModule

import dagger.Binds
import dagger.Module

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, VibrationPresenterModule::class))
abstract class VibrationFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: VibrationFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: VibrationFragment): VibrationView

}
