package com.omvp.app.ui.samples.samplepager.view


import android.os.Bundle
import android.support.v7.widget.AppCompatTextView

import com.omvp.app.R
import com.omvp.app.ui.samples.samplepager.presenter.SamplePagerFirstPresenterImpl

import butterknife.BindView

class SamplePagerFirstFragment :
        SamplePagerFragment<SamplePagerFirstPresenterImpl, SamplePagerFirstFragment.SamplePagerFirstFragmentCallback>(),
        SamplePagerFirstView {

    @BindView(R.id.text)
    internal lateinit var mTextView: AppCompatTextView

    interface SamplePagerFirstFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
        mTextView.text = text
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SamplePagerFirstFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
