package com.omvp.app.ui.samples.pager.view


import android.os.Bundle
import com.omvp.app.ui.samples.pager.presenter.SamplePagerSecondPresenterImpl
import kotlinx.android.synthetic.main.sample_pager_second_fragment.*

class SamplePagerSecondFragment :
        SamplePagerFragment<SamplePagerSecondPresenterImpl, SamplePagerSecondFragment.SamplePagerSecondFragmentCallback>(),
        SamplePagerSecondView {

    interface SamplePagerSecondFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
        text_view.text = text
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SamplePagerSecondFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
