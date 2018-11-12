package com.omvp.app.ui.samples.coroutine.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides CoroutineTopPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class CoroutinePresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: CoroutinePresenterImpl): CoroutinePresenter

}