package com.omvp.app.ui.samples.location.view

import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import butterknife.BindView
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.location.presenter.SampleLocationPresenter

class SampleLocationFragment : BaseViewFragment<SampleLocationPresenter,
        SampleLocationFragment.FragmentCallback>(),
        SampleLocationView {

    @BindView(R.id.longitude_value)
    internal lateinit var mLongitude: AppCompatTextView

    @BindView(R.id.latitude_value)
    internal lateinit var mLatitude: AppCompatTextView

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    override fun drawLocation(latitude: String, longitude: String) {
        mLongitude.text = longitude
        mLatitude.text = latitude
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleLocationFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
