package com.omvp.app.ui.samples.noticedialog.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.noticedialog.view.SampleNoticeView

import javax.inject.Inject

class SampleNoticePresenterImpl @Inject
constructor(sampleNoticeView: SampleNoticeView) : BasePresenter<SampleNoticeView>(sampleNoticeView), SampleNoticePresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()
        showDialog()
    }

    private fun showDialog() {
        mView?.showError(200, "Test Dialog", "This is a sample message to test the dialog")
    }

}
