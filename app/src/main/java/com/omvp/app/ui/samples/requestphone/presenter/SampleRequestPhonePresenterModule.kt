package com.omvp.app.ui.samples.requestphone.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment
import dagger.Binds
import dagger.Module


@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleRequestPhonePresenterModule {

    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleRequestPhonePresenterImpl): SampleRequestPhonePresenter
}
