package com.omvp.app.ui.samples.requestphone.presenter

import com.omvp.app.base.mvp.presenter.Presenter

interface SampleRequestPhonePresenter : Presenter {

    fun validatePhone(prefix: String, phone: String)
}
