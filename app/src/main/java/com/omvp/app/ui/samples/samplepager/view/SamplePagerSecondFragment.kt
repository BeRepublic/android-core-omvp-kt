package com.omvp.app.ui.samples.samplepager.view


import android.os.Bundle
import android.support.v7.widget.AppCompatTextView

import com.omvp.app.R
import com.omvp.app.ui.samples.samplepager.presenter.SamplePagerSecondPresenterImpl

import butterknife.BindView

class SamplePagerSecondFragment :
        SamplePagerFragment<SamplePagerSecondPresenterImpl, SamplePagerSecondFragment.SamplePagerSecondFragmentCallback>(),
        SamplePagerSecondView {

    @BindView(R.id.text)
    internal lateinit var mTextView: AppCompatTextView

    interface SamplePagerSecondFragmentCallback : SamplePagerFragment.SamplePagerFragmentCallback

    override fun drawText(text: String) {
        mTextView.text = text
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SamplePagerSecondFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
