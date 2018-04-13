package com.omvp.app.ui.samples.pager.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SamplePagerPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SamplePagerPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun firstPresenter(presenter: SamplePagerFirstPresenterImpl): SamplePagerFirstPresenter

    @Binds
    @PerFragment
    internal abstract fun secondPresenter(presenter: SamplePagerSecondPresenterImpl): SamplePagerSecondPresenter
}