package com.omvp.app.ui.samples.social.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment
import dagger.Binds
import dagger.Module


/**
 * Provides SampleSocialPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class SampleSocialPresenterModule {
    @Binds
    @PerFragment
    internal abstract fun presenter(presenter: SampleSocialPresenterImpl): SampleSocialPresenter
}
