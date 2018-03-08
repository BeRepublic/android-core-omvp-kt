package com.omvp.app.ui.samples.samplepager.presenter


import com.omvp.app.ui.samples.samplepager.view.SamplePagerSecondFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerSecondView

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
