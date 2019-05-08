package com.omvp.app.ui.samples.requestphone.presenter

import com.omvp.app.R
import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptor
import com.omvp.app.ui.samples.requestphone.view.SampleRequestPhoneView
import javax.inject.Inject

class SampleRequestPhonePresenterImpl
@Inject
constructor(sampleRequestPhoneView: SampleRequestPhoneView) : BasePresenter<SampleRequestPhoneView>(sampleRequestPhoneView), SampleRequestPhonePresenter {

    @Inject
    internal lateinit var mAuthPhoneInterceptor: AuthPhoneInterceptor

    override fun validatePhone(prefix: String, phone: String) {
        var formValid = true
        if (prefix.isEmpty()) {
            showPrefixInputError(mContext.getString(R.string.invalid_prefix))
            formValid = false
        } else run { showPrefixInputSuccess() }

        if (phone.isEmpty()) {
            showPhoneInputError(mContext.getString(R.string.enter_your_phone))
            formValid = false
        } else run { showPhoneInputSuccess() }
        if (formValid) {
            startPhoneNumberVerification("+$prefix$phone")
        }
    }

    private fun startPhoneNumberVerification(number: String) {
        showProgress()
        mAuthPhoneInterceptor.startPhoneNumberVerification(number)
    }

    private fun showPrefixInputError(error: String) {
        mView?.showPrefixInputError(error)
    }

    private fun showPhoneInputError(error: String) {
        mView?.showPhoneInputError(error)
    }

    private fun showPrefixInputSuccess() {
        mView?.showPrefixInputSuccess()
    }

    private fun showPhoneInputSuccess() {
        mView?.showPhoneInputSuccess()
    }
}
