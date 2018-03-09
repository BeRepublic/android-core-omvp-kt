package com.omvp.app.ui.samples.samplelocation.presenter


import android.location.Location
import android.support.annotation.NonNull

import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.samplelocation.view.SampleLocationView

import javax.inject.Inject

class SampleLocationPresenterImpl
@Inject
internal constructor(sampleView: SampleLocationView) : BasePresenter<SampleLocationView>(sampleView),
        SampleLocationPresenter {

    private var mCurrentLocation: Location? = null

    override fun locationChanged(location: Location?) {
        mCurrentLocation = location
        if (mCurrentLocation != null) {
            getLocationData(mCurrentLocation!!)
        }
    }

    private fun getLocationData(@NonNull location: Location) {
        mView?.drawLocation(location.latitude.toString(), location.longitude.toString())
    }
}
