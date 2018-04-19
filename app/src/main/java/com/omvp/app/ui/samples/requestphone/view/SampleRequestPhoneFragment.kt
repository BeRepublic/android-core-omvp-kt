package com.omvp.app.ui.samples.requestphone.view

import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.RelativeLayout
import butterknife.BindView
import butterknife.OnClick
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterImpl
import com.omvp.components.InputLayoutView

class SampleRequestPhoneFragment : BaseViewFragment<SampleRequestPhonePresenterImpl, SampleRequestPhoneFragment.FragmentCallback>(),  SampleRequestPhoneView {

    @BindView(R.id.input_prefix)
    internal lateinit var mInputPrefixView: InputLayoutView
    @BindView(R.id.input_phone)
    internal lateinit var mInputPhoneView: InputLayoutView
    @BindView(R.id.code_received_layout)
    internal lateinit var mCodeReceivedLayout: RelativeLayout
    @BindView(R.id.input_code)
    internal lateinit var mInputCodeToSent: InputLayoutView
    @BindView(R.id.send)
    internal lateinit var mButtonGetSent: AppCompatTextView
    @BindView(R.id.sign_out)
    internal lateinit var mButtonSignOut: AppCompatTextView

    interface FragmentCallback : BaseViewFragmentCallback {
        fun signOut()
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleRequestPhoneFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }


    @OnClick(R.id.send)
    fun onValidatePhoneClick(view: View) {
        mPresenter.validatePhone(mInputPrefixView.text, mInputPhoneView.text)
    }

    @OnClick(R.id.sign_out)
    fun onSignOutClick(view: View) {
        if (mCallback != null) {
            mCallback.signOut()
        }
    }

    override fun showPrefixInputError(error: String) {
        mInputPrefixView.setError(error)
    }

    override fun showPhoneInputError(error: String) {
        mInputPhoneView.setError(error)
    }

    override fun showPrefixInputSuccess() {
        mInputPrefixView.setSuccess()
    }

    override fun showPhoneInputSuccess() {
        mInputPhoneView.setSuccess()
    }

    override fun drawInputCode(code: String) {
        mInputCodeToSent.text = code
    }

    override fun codeReceivedLayoutVisibility(visibility: Int) {
        mCodeReceivedLayout.visibility = visibility
    }

    fun changeButtonText(title: String) {
        mButtonGetSent.text = title
    }

    fun singOutButtonVisibility(visibility: Int) {
        mButtonSignOut.visibility = visibility
    }
}
