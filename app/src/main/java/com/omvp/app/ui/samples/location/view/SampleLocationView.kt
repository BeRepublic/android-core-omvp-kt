package com.omvp.app.ui.samples.location.view

import com.omvp.app.base.mvp.view.BaseView

interface SampleLocationView : BaseView {

    fun drawLocation(latitude: String, longitude: String)
}
