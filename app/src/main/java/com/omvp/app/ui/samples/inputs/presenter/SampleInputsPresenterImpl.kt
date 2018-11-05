package com.omvp.app.ui.samples.inputs.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.inputs.view.SampleInputView
import com.omvp.app.util.ValidationHelper
import com.raxdenstudios.commons.util.Utils

import javax.inject.Inject

class SampleInputsPresenterImpl @Inject
constructor(sampleInputView: SampleInputView) : BasePresenter<SampleInputView>(sampleInputView), SampleInputsPresenter {

    override fun validateInputs(name: String, password: String, fixed: String, fixedLeft: String, fixedCenter: String) {
        if (validateForm(name, password, fixed, fixedLeft, fixedCenter)) {
            showValidationToast()
        }
    }

    private fun validateForm(name: String, password: String, fixed: String, fixedLeft: String, fixedCenter: String): Boolean {
        var formValid = true
        if (!Utils.hasValue(name)) {
            showNameInputError("string.enter_your_name")
            formValid = false
        } else {
            showNameInputSuccess()
        }
        if (!Utils.hasValue(password)) {
            showPasswordInputError("string.enter_password")
            formValid = false
        } else {
            showPasswordInputSuccess()
        }
        if (!ValidationHelper.validatePassword(password)) {
            showPasswordInputError("string.password_length_min_6")
            formValid = false
        } else {
            showPasswordInputSuccess()
        }
        if (!Utils.hasValue(fixed)) {
            showFixedInputError("string.enter_fixed")
            formValid = false
        } else {
            showFixedInputSuccess()
        }
        if (!Utils.hasValue(fixedLeft)) {
            showFixedLeftInputError("string.enter_fixed_left")
            formValid = false
        } else {
            showFixedLeftInputSuccess()
        }
        if (!Utils.hasValue(fixedCenter)) {
            showFixedCenterInputError("string.enter_fixed_center")
            formValid = false
        } else {
            showFixedCenterInputSuccess()
        }
        return formValid
    }

    private fun showNameInputError(error: String) {
        mView?.showNameInputError(error)
    }

    private fun showNameInputSuccess() {
        mView?.showNameInputSuccess()
    }

    private fun showPasswordInputError(error: String) {
        mView?.showPasswordInputError(error)
    }

    private fun showPasswordInputSuccess() {
        mView?.showPasswordInputSuccess()
    }

    private fun showFixedInputError(error: String) {
        mView?.showFixedInputError(error)
    }

    private fun showFixedInputSuccess() {
        mView?.showFixedInputSuccess()
    }

    private fun showFixedLeftInputError(error: String) {
        mView?.showFixedLeftInputError(error)
    }

    private fun showFixedLeftInputSuccess() {
        mView?.showFixedLeftInputSuccess()
    }

    private fun showFixedCenterInputError(error: String) {
        mView?.showFixedCenterInputError(error)
    }

    private fun showFixedCenterInputSuccess() {
        mView?.showFixedCenterInputSuccess()
    }

    private fun showValidationToast() {
        mView?.showValidationToast()
    }
}
