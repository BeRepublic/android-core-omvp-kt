package com.omvp.app.ui.samples.takepicture.view

import android.support.v4.app.Fragment

import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.takepicture.presenter.SampleTakePicturePresenterModule

import dagger.Binds
import dagger.Module

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleTakePicturePresenterModule::class))
abstract class SampleTakePictureFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: SampleTakePictureFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleTakePictureFragment): SampleTakePictureView

}
