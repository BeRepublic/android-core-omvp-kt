package com.omvp.app.dialog.notice.view

import android.os.Bundle
import android.view.View
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.dialog.notice.presenter.NoticeDialogPresenter
import kotlinx.android.synthetic.main.notice_dialog_fragment.*

class NoticeDialogFragment : BaseViewFragment<NoticeDialogPresenter, NoticeDialogFragment.FragmentCallback>(), NoticeDialogView {

    private var titleText: String? = null
    private var descriptionText: String? = null
    private var acceptText: String? = null
    private var denyText: String? = null
    private var onAcceptClickListener: View.OnClickListener? = null
    private var onDenyClickListener: View.OnClickListener? = null

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)
        setupViews()
    }

    private fun setupViews() {
        notice_dialog_view.takeIf { !titleText.isNullOrEmpty() }.also { it?.setTitleText(titleText!!) }
        notice_dialog_view.takeIf { !descriptionText.isNullOrEmpty() }.also { it?.setDescriptionText(descriptionText!!) }
        notice_dialog_view.takeIf { !acceptText.isNullOrEmpty() }.also { it?.setAcceptTextButton(acceptText!!, onAcceptClickListener!!) }
        notice_dialog_view.takeIf { !denyText.isNullOrEmpty() }.also { it?.setDenyTextButton(denyText!!, onDenyClickListener!!) }
    }

    fun setTitle(titleText: String) {
        this.titleText = titleText
    }

    fun setDescription(descriptionText: String) {
        this.descriptionText = descriptionText
    }

    fun setAcceptButton(acceptText: String, onClickListener: View.OnClickListener) {
        this.acceptText = acceptText
        this.onAcceptClickListener = onClickListener
    }

    fun setDenyButton(denyText: String, onClickListener: View.OnClickListener) {
        this.denyText = denyText
        this.onDenyClickListener = onClickListener
    }

    companion object {
        fun newInstance(bundle: Bundle?) = NoticeDialogFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}

