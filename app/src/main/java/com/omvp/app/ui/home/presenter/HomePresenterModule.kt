package com.omvp.app.ui.home.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleListPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class HomePresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: HomePresenterImpl): HomePresenter

}