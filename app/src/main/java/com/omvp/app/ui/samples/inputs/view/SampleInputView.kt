package com.omvp.app.ui.samples.inputs.view

import com.omvp.app.base.mvp.view.BaseView

/**
 * Created by Angel on 21/02/2018.
 */
interface SampleInputView : BaseView {

    fun showNameInputError(error: String)

    fun showNameInputSuccess()

    fun showPasswordInputError(error: String)

    fun showPasswordInputSuccess()

    fun showValidationToast()

    fun showFixedInputError(error: String)

    fun showFixedInputSuccess()

    fun showFixedLeftInputError(error: String)

    fun showFixedLeftInputSuccess()

    fun showFixedCenterInputError(error: String)

    fun showFixedCenterInputSuccess()
}
