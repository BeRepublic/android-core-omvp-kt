package com.omvp.app.ui.samples.bottomnavigation.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationPresenterModule
import dagger.Binds
import dagger.Module

/**
 * Provides SamplePagerFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, BottomNavigationPresenterModule::class))
abstract class BottomNavigationSecondFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SamplePagerFragment
     * @return the fragment
     */

    @Binds
    @PerFragment
    internal abstract fun secondFragment(fragment: BottomNavigationSecondFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun secondView(fragment: BottomNavigationSecondFragment): BottomNavigationView
}
