package com.omvp.app.ui.samples.requestphone.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterImpl
import kotlinx.android.synthetic.main.sample_request_phone_fragment.*

class SampleRequestPhoneFragment : BaseViewFragment<SampleRequestPhonePresenterImpl, SampleRequestPhoneFragment.FragmentCallback>(), SampleRequestPhoneView {

    interface FragmentCallback : BaseViewFragmentCallback {
        fun signOut()
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleRequestPhoneFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        sign_out.setOnClickListener { onSignOutClick() }
    }

    override fun showPrefixInputError(error: String) {
        input_prefix.setError(error)
    }

    override fun showPhoneInputError(error: String) {
        input_phone.setError(error)
    }

    override fun showPrefixInputSuccess() {
        input_prefix.setSuccess()
    }

    override fun showPhoneInputSuccess() {
        input_phone.setSuccess()
    }

    override fun drawInputCode(code: String) {
        input_code.text = code
    }

    override fun codeReceivedLayoutVisibility(visibility: Int) {
        code_received_layout.visibility = visibility
    }

    fun changeButtonText(title: String) {
        send.text = title
    }

    fun singOutButtonVisibility(visibility: Int) {
        sign_out.visibility = visibility
    }

    private fun onValidatePhoneClick() {
        mPresenter.validatePhone(input_prefix.text, input_phone.text)
    }

    private fun onSignOutClick() {
        mCallback.signOut()
    }
}
