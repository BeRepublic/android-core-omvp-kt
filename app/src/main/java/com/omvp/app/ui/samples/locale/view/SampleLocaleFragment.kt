package com.omvp.app.ui.samples.locale.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.locale.presenter.SampleLocalePresenterImpl
import kotlinx.android.synthetic.main.sample_locale_fragment.*

class SampleLocaleFragment : BaseViewFragment<SampleLocalePresenterImpl, SampleLocaleFragment.FragmentCallback>(), SampleLocaleView {
    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)
        setupViews()
    }

    private fun setupViews() {
        locale_selector.setOnClickListener { onLocaleChangeButtonSelected() }
    }

    override fun drawLocale(locale: String) {
        current_locale.text = locale
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

    private fun onLocaleChangeButtonSelected() {
        mPresenter.changeLocale()
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleLocaleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

}
