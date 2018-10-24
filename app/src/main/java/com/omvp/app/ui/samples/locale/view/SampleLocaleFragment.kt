package com.omvp.app.ui.samples.locale.view

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.locale.presenter.SampleLocalePresenterImpl

class SampleLocaleFragment : BaseViewFragment<SampleLocalePresenterImpl, SampleLocaleFragment.FragmentCallback>(), SampleLocaleView {

//    internal lateinit var mLocaleValue: AppCompatTextView
//    internal lateinit var mLocaleSelector: AppCompatButton

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    override fun drawLocale(locale: String) {
//        mLocaleValue.text = locale
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

    fun onLocaleChangeButtonSelected() {
        mPresenter.changeLocale()
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleLocaleFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

}
