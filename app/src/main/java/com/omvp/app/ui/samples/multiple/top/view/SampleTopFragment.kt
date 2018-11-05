package com.omvp.app.ui.samples.multiple.top.view

import android.os.Bundle

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.multiple.top.presenter.SampleTopPresenter

class SampleTopFragment :
        BaseViewFragment<SampleTopPresenter, SampleTopFragment.FragmentCallback>(),
        SampleTopView {

    interface FragmentCallback : BaseViewFragmentCallback

    companion object {
        fun newInstance(bundle: Bundle?) = SampleTopFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
