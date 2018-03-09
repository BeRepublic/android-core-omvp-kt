package com.omvp.app.ui.samples.samplelocation.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides SampleTopPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleLocationPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleLocationPresenterImpl): SampleLocationPresenter

}
