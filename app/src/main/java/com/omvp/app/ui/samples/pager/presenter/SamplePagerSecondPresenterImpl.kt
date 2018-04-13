package com.omvp.app.ui.samples.pager.presenter


import com.omvp.app.ui.samples.pager.view.SamplePagerSecondFragment
import com.omvp.app.ui.samples.pager.view.SamplePagerSecondView

import javax.inject.Inject

class SamplePagerSecondPresenterImpl
@Inject
constructor(view: SamplePagerSecondFragment) :
        SamplePagerPresenterImpl<SamplePagerSecondView>(view),
        SamplePagerSecondPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

        drawText("Fragment #2")
    }

    private fun drawText(text: String) {
        mView?.drawText(text)
    }
}
