package com.omvp.app.ui.samples.detail.view

import android.os.Bundle
import android.support.v7.widget.AppCompatTextView

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.detail.presenter.SampleDetailPresenterImpl

import butterknife.BindView

class SampleDetailFragment : BaseViewFragment<SampleDetailPresenterImpl, SampleDetailFragment.FragmentCallback>(), SampleDetailView {

    @BindView(R.id.text)
    internal lateinit var mTextView: AppCompatTextView
    @BindView(R.id.title)
    internal lateinit var mTitleView: AppCompatTextView

    interface FragmentCallback : BaseViewFragmentCallback {
        fun drawImage(imageRes: Int)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupViews()
    }

    override fun drawImage(imageRes: Int) {
        mCallback.drawImage(imageRes)
    }

    override fun drawText(text: String) {
        mTextView.text = text
    }

    override fun drawTitle(title: String) {
        mTitleView.text = title
    }

    private fun setupViews() {

    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleDetailFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
