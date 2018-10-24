package com.omvp.app.ui.samples.pager.view


import android.os.Bundle
import com.omvp.app.ui.samples.pager.presenter.SamplePagerFirstPresenterImpl

class SamplePagerFirstFragment :
        SamplePagerFragment<SamplePagerFirstPresenterImpl, SamplePagerFirstFragment.SamplePagerFirstFragmentCallback>(),
        SamplePagerFirstView {

//    internal lateinit var mTextView: AppCompatTextView

    interface SamplePagerFirstFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
//        mTextView.text = text
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SamplePagerFirstFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
