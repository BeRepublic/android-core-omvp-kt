package com.omvp.app.ui.samples.pager.view


import android.os.Bundle
import com.omvp.app.ui.samples.pager.presenter.SamplePagerSecondPresenterImpl

class SamplePagerSecondFragment :
        SamplePagerFragment<SamplePagerSecondPresenterImpl, SamplePagerSecondFragment.SamplePagerSecondFragmentCallback>(),
        SamplePagerSecondView {

//    internal lateinit var mTextView: AppCompatTextView

    interface SamplePagerSecondFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
//        mTextView.text = text
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SamplePagerSecondFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
