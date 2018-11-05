package com.omvp.app.ui.samples.location.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.location.presenter.SampleLocationPresenter
import kotlinx.android.synthetic.main.sample_location_fragment.*

class SampleLocationFragment : BaseViewFragment<SampleLocationPresenter,
        SampleLocationFragment.FragmentCallback>(),
        SampleLocationView {

    interface FragmentCallback : BaseViewFragmentCallback

    override fun drawLocation(latitude: String, longitude: String) {
        latitude_value.text = longitude
        longitude_value.text = latitude
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleLocationFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
