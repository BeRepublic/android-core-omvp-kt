package com.omvp.app.ui.samples.listhorizontal.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleListHorizontalPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleListHorizontalPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleListHorizontalPresenterImpl): SampleListHorizontalPresenter

}