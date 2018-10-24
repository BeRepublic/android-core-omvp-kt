package com.omvp.app.ui.samples.home.view


import android.support.v4.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.home.presenter.HomePresenterModule

import dagger.Binds
import dagger.Module

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, HomePresenterModule::class))
abstract class HomeFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the HomeFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    internal abstract fun fragment(fragment: HomeFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: HomeFragment): HomeView

}
