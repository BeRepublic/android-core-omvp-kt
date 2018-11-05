package com.omvp.app.ui.samples.pager.view


import android.os.Bundle
import com.omvp.app.ui.samples.pager.presenter.SamplePagerFirstPresenterImpl
import kotlinx.android.synthetic.main.sample_pager_first_fragment.*

class SamplePagerFirstFragment :
        SamplePagerFragment<SamplePagerFirstPresenterImpl, SamplePagerFirstFragment.SamplePagerFirstFragmentCallback>(),
        SamplePagerFirstView {

    interface SamplePagerFirstFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
        text_view.text = text
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SamplePagerFirstFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
