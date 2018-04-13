package com.omvp.app.ui.samples.multiple.top.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleTopPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleTopPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleTopPresenterImpl): SampleTopPresenter

}