package com.omvp.app.ui.samples.noticedialog.view

import android.os.Bundle

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.noticedialog.presenter.SampleNoticePresenter

class SampleNoticeFragment : BaseViewFragment<SampleNoticePresenter, SampleNoticeFragment.FragmentCallback>(), SampleNoticeView {

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleNoticeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

}
