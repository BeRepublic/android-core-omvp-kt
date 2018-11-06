package com.omvp.app.ui.samples.detail.view

import android.os.Bundle
import android.view.View
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.detail.presenter.SampleDetailPresenterImpl
import kotlinx.android.synthetic.main.sample_detail_fragment.*

class SampleDetailFragment : BaseViewFragment<SampleDetailPresenterImpl, SampleDetailFragment.FragmentCallback>(), SampleDetailView {
    
    interface FragmentCallback : BaseViewFragmentCallback {
        fun drawImage(imageRes: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setSampleId(arguments?.getString(String::class.java.simpleName))
    }

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)
        setupViews()
    }

    override fun drawImage(imageRes: Int) {
        mCallback.drawImage(imageRes)
    }

    override fun drawText(text: String) {
        text_view.text = text
    }

    override fun drawTitle(title: String) {
        title_view.text = title
    }

    private fun setupViews() {

    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleDetailFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
