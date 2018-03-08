package com.omvp.app.ui.samples.samplemultiple.top.view

import android.os.Bundle

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.samplemultiple.top.presenter.SampleTopPresenter
import com.omvp.app.ui.samples.samplemultiple.top.view.SampleTopView

class SampleTopFragment :
        BaseViewFragment<SampleTopPresenter, SampleTopFragment.FragmentCallback>(),
        SampleTopView {

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleTopFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
