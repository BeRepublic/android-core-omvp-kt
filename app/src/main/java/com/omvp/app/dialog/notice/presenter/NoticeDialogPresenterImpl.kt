package com.omvp.app.dialog.notice.presenter

import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.dialog.notice.view.NoticeDialogView

import javax.inject.Inject

class NoticeDialogPresenterImpl @Inject
constructor(noticeDialogView: NoticeDialogView) : BasePresenter<NoticeDialogView>(noticeDialogView), NoticeDialogPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()
    }

}
