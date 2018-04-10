package com.omvp.app.dialog.notice.presenter

import com.omvp.app.base.mvp.presenter.BasePresenterModule
import com.omvp.app.injector.scope.PerFragment

import dagger.Binds
import dagger.Module

/**
 * Provides NoticeDialogPresenterModule dependencies.
 */
@Module(includes = arrayOf(BasePresenterModule::class))
abstract class NoticeDialogPresenterModule {

    @Binds
    @PerFragment
    internal abstract fun dialogPresenter(presenter: NoticeDialogPresenterImpl): NoticeDialogPresenter

}
