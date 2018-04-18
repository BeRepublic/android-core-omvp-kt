package com.omvp.app.ui.samples.location.presenter


import android.location.Location
import com.google.android.gms.location.LocationListener
import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.interceptor.location.LocationInterceptor
import com.omvp.app.ui.samples.location.view.SampleLocationView
import javax.inject.Inject

class SampleLocationPresenterImpl
@Inject
internal constructor(sampleView: SampleLocationView) : BasePresenter<SampleLocationView>(sampleView),
        SampleLocationPresenter {

    @Inject
    internal lateinit var mLocationInterceptor: LocationInterceptor

    override fun onViewLoaded() {
        super.onViewLoaded()
        mLocationInterceptor.addLocationListener(LocationListener { location -> drawLocation(location) })
    }

    private fun drawLocation(location: Location?) {
        if (mView != null) {
            mView!!.drawLocation(
                    location?.latitude?.toString() ?: "",
                    location?.longitude?.toString() ?: "")
        }
    }
}
