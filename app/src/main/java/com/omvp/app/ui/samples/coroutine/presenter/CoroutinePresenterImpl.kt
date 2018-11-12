package com.omvp.app.ui.samples.coroutine.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.coroutine.view.CoroutineView
import javax.inject.Inject

class CoroutinePresenterImpl
@Inject internal constructor(sampleView: CoroutineView) : BasePresenter<CoroutineView>(sampleView),
        CoroutinePresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

    }

}
