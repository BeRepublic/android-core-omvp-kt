package com.omvp.app.ui.samples.detail.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.detail.presenter.SampleDetailPresenterImpl

class SampleDetailFragment : BaseViewFragment<SampleDetailPresenterImpl, SampleDetailFragment.FragmentCallback>(), SampleDetailView {

//    internal lateinit var mTextView: AppCompatTextView
//    internal lateinit var mTitleView: AppCompatTextView

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
//        mTextView.text = text
    }

    override fun drawTitle(title: String) {
//        mTitleView.text = title
    }

    private fun setupViews() {

    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleDetailFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
