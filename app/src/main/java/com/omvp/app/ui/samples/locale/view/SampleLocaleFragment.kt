package com.omvp.app.ui.samples.locale.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.ArrayAdapter

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.locale.presenter.SampleLocalePresenterImpl

import butterknife.BindView
import butterknife.OnClick

class SampleLocaleFragment : BaseViewFragment<SampleLocalePresenterImpl, SampleLocaleFragment.FragmentCallback>(), SampleLocaleView {

    @BindView(R.id.locale_value)
    internal lateinit var mLocaleValue: AppCompatTextView
    @BindView(R.id.locale_selector)
    internal lateinit var mLocaleSelector: AppCompatButton

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    override fun drawLocale(locale: String) {
        mLocaleValue.text = locale
    }

    override fun drawLocaleSelector(localeList: List<String>, selection: Int) {
        AlertDialog.Builder(activity)
                .setSingleChoiceItems(
                        ArrayAdapter(mContext, android.R.layout.select_dialog_singlechoice, localeList),
                        selection
                ) { dialog, which ->
                    mPresenter.localeSelected(which)
                    dialog.dismiss()
                }
                .setTitle("Elige un idioma")
                .create()
                .show()
    }

    @OnClick(R.id.locale_selector)
    fun onLocaleChangeButtonSelected(view: View) {
        mPresenter.changeLocale()
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleLocaleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

}
