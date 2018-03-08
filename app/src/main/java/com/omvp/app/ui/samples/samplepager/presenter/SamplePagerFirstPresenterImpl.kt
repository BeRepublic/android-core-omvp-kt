package com.omvp.app.ui.samples.samplepager.presenter


import com.omvp.app.ui.samples.samplepager.view.SamplePagerFirstFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerFirstView

import javax.inject.Inject

class SamplePagerFirstPresenterImpl
@Inject
constructor(view: SamplePagerFirstFragment) :
        SamplePagerPresenterImpl<SamplePagerFirstView>(view),
        SamplePagerFirstPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

        drawText("Fragment #1")
    }

    private fun drawText(text: String) {
        mView?.drawText(text)
    }
}
