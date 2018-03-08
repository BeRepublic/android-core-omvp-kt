package com.omvp.app.ui.samples.samplemultiple.bottom.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleBottomPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleBottomPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleBottomPresenterImpl): SampleBottomPresenter

}