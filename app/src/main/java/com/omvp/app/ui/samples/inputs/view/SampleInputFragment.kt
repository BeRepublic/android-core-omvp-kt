package com.omvp.app.ui.samples.inputs.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.inputs.presenter.SampleInputsPresenterImpl
import kotlinx.android.synthetic.main.sample_input_fragment.*

class SampleInputFragment : BaseViewFragment<SampleInputsPresenterImpl, SampleInputFragment.FragmentCallback>(), SampleInputView {

    private val onNameTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(s: Editable) {
            input_name.clearError()
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
            input_password.clearError()
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
            input_fixed.clearError()
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
            input_fixed_left.clearError()
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
            input_fixed_center.clearError()
        }
    }

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    fun onValidationClicked(view: View) {
        mPresenter.validateInputs(
                input_name.text,
                input_password.text,
                input_fixed.text,
                input_fixed_left.text,
                input_fixed_center.text
        )
    }

    override fun showNameInputError(error: String) {
        input_name.setError(error)
    }

    override fun showNameInputSuccess() {
        input_name.setSuccess()
    }

    override fun showValidationToast() {
        Toast.makeText(mContext, "Validation succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun showFixedInputError(error: String) {
        input_fixed.setError(error)
    }

    override fun showFixedInputSuccess() {
        input_fixed.setSuccess()
    }

    override fun showFixedLeftInputError(error: String) {
        input_fixed_left.setError(error)
    }

    override fun showFixedLeftInputSuccess() {
        input_fixed_left.setSuccess()
    }

    override fun showFixedCenterInputError(error: String) {
        input_fixed_center.setError(error)
    }

    override fun showFixedCenterInputSuccess() {
        input_fixed_center.setSuccess()
    }

    override fun showPasswordInputError(error: String) {
        input_password.setError(error)
    }

    override fun showPasswordInputSuccess() {
        input_password.setSuccess()
    }

    private fun setupViews() {
        input_name.addTextChangedListener(onNameTextChanged)
        input_password.addTextChangedListener(onPasswordTextChanged)
        input_fixed.addTextChangedListener(onFixedTextChanged)
        input_fixed_left.addTextChangedListener(onFixedLeftTextChanged)
        input_fixed_center.addTextChangedListener(onFixedCenterTextChanged)
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SampleInputFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
