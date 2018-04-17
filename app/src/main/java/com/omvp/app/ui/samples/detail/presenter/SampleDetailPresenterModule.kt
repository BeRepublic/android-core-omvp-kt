package com.omvp.app.ui.samples.detail.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleDetailPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleDetailPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleDetailPresenterImpl): SampleDetailPresenter

}