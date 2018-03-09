package com.omvp.app.ui.samples.samplelocation.view

import android.location.Location
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.samplelocation.presenter.SampleLocationPresenter

import butterknife.BindView

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

    fun locationChanged(location: Location?) {
        mPresenter.locationChanged(location)
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
