package com.omvp.app.ui.samples.multiple.bottom.view

import android.os.Bundle

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.multiple.bottom.presenter.SampleBottomPresenter

class SampleBottomFragment :
        BaseViewFragment<SampleBottomPresenter, SampleBottomFragment.FragmentCallback>(),
        SampleBottomView {

    interface FragmentCallback : BaseViewFragmentCallback

    companion object {

        fun newInstance(bundle: Bundle?) = SampleBottomFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
