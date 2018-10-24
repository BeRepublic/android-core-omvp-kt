package com.omvp.app.ui.samples.inputs.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.inputs.presenter.SampleInputsPresenterImpl

class SampleInputFragment : BaseViewFragment<SampleInputsPresenterImpl, SampleInputFragment.FragmentCallback>(), SampleInputView {

//    internal lateinit var mNameInputLayout: InputLayoutView
//    internal lateinit var mPasswordInputLayout: InputLayoutView
//    internal lateinit var mFixedInputLayout: InputLayoutView
//    internal lateinit var mFixedLeftInputLayout: InputLayoutView
//    internal lateinit var mFixedCenterInputLayout: InputLayoutView

    private val onNameTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            mPresenter.nameChanged(s.toString())
        }
    }

    private val onPasswordTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            mPresenter.passwordChanged(s.toString())
        }
    }

    private val onFixedTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            mPresenter.fixedChanged(s.toString())
        }
    }

    private val onFixedLeftTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            mPresenter.fixedLeftChanged(s.toString())
        }
    }

    private val onFixedCenterTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            mPresenter.fixedCenterChanged(s.toString())
        }
    }

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    fun onValidationClicked(view: View) {
        /*mPresenter.validateInputs(
                mNameInputLayout.text,
                mPasswordInputLayout.text,
                mFixedInputLayout.text,
                mFixedLeftInputLayout.text,
                mFixedCenterInputLayout.text
        )*/
    }

    override fun showNameInputError(error: String) {
//        mNameInputLayout.setError(error)
    }

    override fun showNameInputSuccess() {
//        mNameInputLayout.setSuccess()
    }

    override fun showValidationToast() {
        Toast.makeText(mContext, "Validation succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun showFixedInputError(error: String) {
//        mFixedInputLayout.setError(error)
    }

    override fun showFixedInputSuccess() {
//        mFixedInputLayout.setSuccess()
    }

    override fun hideFixedInputError() {
//        mFixedInputLayout.clearError()
    }

    override fun showFixedLeftInputError(error: String) {
//        mFixedLeftInputLayout.setError(error)
    }

    override fun showFixedLeftInputSuccess() {
//        mFixedLeftInputLayout.setSuccess()
    }

    override fun hideFixedLeftInputError() {
//        mFixedLeftInputLayout.clearError()
    }

    override fun showFixedCenterInputError(error: String) {
//        mFixedCenterInputLayout.setError(error)
    }

    override fun showFixedCenterInputSuccess() {
//        mFixedCenterInputLayout.setSuccess()
    }

    override fun hideFixedCenterInputError() {
//        mFixedCenterInputLayout.clearError()
    }

    override fun hideNameInputError() {
//        mNameInputLayout.clearError()
    }

    override fun showPasswordInputError(error: String) {
//        mPasswordInputLayout.setError(error)
    }

    override fun showPasswordInputSuccess() {
//        mPasswordInputLayout.setSuccess()
    }

    override fun hidePasswordInputError() {
//        mPasswordInputLayout.clearError()
    }

    private fun setupViews() {
//        mNameInputLayout.addTextChangedListener(onNameTextChanged)
//        mPasswordInputLayout.addTextChangedListener(onPasswordTextChanged)
//        mFixedInputLayout.addTextChangedListener(onFixedTextChanged)
//        mFixedLeftInputLayout.addTextChangedListener(onFixedLeftTextChanged)
//        mFixedCenterInputLayout.addTextChangedListener(onFixedCenterTextChanged)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleInputFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
