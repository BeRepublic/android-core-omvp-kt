package com.omvp.app.ui.samples.bottomnavigation.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SamplePagerPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class BottomNavigationPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun firstPresenter(presenter: BottomNavigationFirstPresenterImpl): BottomNavigationFirstPresenter

    @Binds
    @PerFragment
    internal abstract fun secondPresenter(presenter: BottomNavigationSecondPresenterImpl): BottomNavigationSecondPresenter

    @Binds
    @PerFragment
    internal abstract fun thirdPresenter(presenter: BottomNavigationThirdPresenterImpl): BottomNavigationThirdPresenter
}