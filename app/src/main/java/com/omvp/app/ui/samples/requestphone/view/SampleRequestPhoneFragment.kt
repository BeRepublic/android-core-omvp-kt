package com.omvp.app.ui.samples.requestphone.view

import android.os.Bundle
import android.view.View
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterImpl

class SampleRequestPhoneFragment : BaseViewFragment<SampleRequestPhonePresenterImpl, SampleRequestPhoneFragment.FragmentCallback>(),  SampleRequestPhoneView {

//    internal lateinit var mInputPrefixView: InputLayoutView
//    internal lateinit var mInputPhoneView: InputLayoutView
//    internal lateinit var mCodeReceivedLayout: RelativeLayout
//    internal lateinit var mInputCodeToSent: InputLayoutView
//    internal lateinit var mButtonGetSent: AppCompatTextView
//    internal lateinit var mButtonSignOut: AppCompatTextView

    interface FragmentCallback : BaseViewFragmentCallback {
        fun signOut()
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleRequestPhoneFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }


    fun onValidatePhoneClick(view: View) {
//        mPresenter.validatePhone(mInputPrefixView.text, mInputPhoneView.text)
    }

    fun onSignOutClick(view: View) {
        if (mCallback != null) {
            mCallback.signOut()
        }
    }

    override fun showPrefixInputError(error: String) {
//        mInputPrefixView.setError(error)
    }

    override fun showPhoneInputError(error: String) {
//        mInputPhoneView.setError(error)
    }

    override fun showPrefixInputSuccess() {
//        mInputPrefixView.setSuccess()
    }

    override fun showPhoneInputSuccess() {
//        mInputPhoneView.setSuccess()
    }

    override fun drawInputCode(code: String) {
//        mInputCodeToSent.text = code
    }

    override fun codeReceivedLayoutVisibility(visibility: Int) {
//        mCodeReceivedLayout.visibility = visibility
    }

    fun changeButtonText(title: String) {
//        mButtonGetSent.text = title
    }

    fun singOutButtonVisibility(visibility: Int) {
//        mButtonSignOut.visibility = visibility
    }
}
