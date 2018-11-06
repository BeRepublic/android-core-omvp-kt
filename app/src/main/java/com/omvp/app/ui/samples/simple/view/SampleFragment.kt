package com.omvp.app.ui.samples.simple.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.simple.presenter.SamplePresenter

class SampleFragment : BaseViewFragment<SamplePresenter, SampleFragment.FragmentCallback>(), SampleView {

    interface FragmentCallback : BaseViewFragmentCallback

    companion object {

        fun newInstance(bundle: Bundle?) = SampleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
