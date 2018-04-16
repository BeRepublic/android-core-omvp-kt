package com.omvp.app.ui.ktsamples.ktsample.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleTopPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SamplePresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SamplePresenterImpl): SamplePresenter

}