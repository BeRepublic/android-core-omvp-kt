package com.omvp.app.ui.samples.requestphone.view

import com.omvp.app.base.mvp.view.BaseView

interface SampleRequestPhoneView: BaseView {

    fun showPrefixInputError(error: String)

    fun showPhoneInputError(error: String)

    fun showPrefixInputSuccess()

    fun showPhoneInputSuccess()

    fun drawInputCode(code: String)

    fun codeReceivedLayoutVisibility(visibility: Int)
}
