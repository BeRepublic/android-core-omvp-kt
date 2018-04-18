package com.omvp.app.ui.samples.inputs.presenter

import com.omvp.app.base.mvp.presenter.Presenter

/**
 * Created by Angel on 21/02/2018.
 */

interface SampleInputsPresenter : Presenter {
    fun validateInputs(name: String, password: String, fixed: String, fixedLeft: String, fixedCenter: String)

    fun nameChanged(name: String)

    fun passwordChanged(s: String)

    fun fixedChanged(s: String)

    fun fixedLeftChanged(s: String)

    fun fixedCenterChanged(s: String)
}
