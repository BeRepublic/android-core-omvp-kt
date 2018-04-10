package com.omvp.app.dialog.notice.view

import android.os.Bundle
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.dialog.notice.presenter.NoticeDialogPresenter
import com.omvp.components.NoticeDialogComponentView
import com.raxdenstudios.commons.util.Utils

import butterknife.BindView

class NoticeDialogFragment : BaseViewFragment<NoticeDialogPresenter, NoticeDialogFragment.FragmentCallback>(), NoticeDialogView {

    @BindView(R.id.notice_dialog_view)
    internal lateinit var mNoticeDialogComponentView: NoticeDialogComponentView

    private var titleText: String? = null
    private var descriptionText: String? = null
    private var acceptText: String? = null
    private var denyText: String? = null
    private var onAcceptClickListener: View.OnClickListener? = null
    private var onDenyClickListener: View.OnClickListener? = null

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        if (Utils.hasValue(titleText)) {
            mNoticeDialogComponentView.setTitleText(titleText)
        }
        if (Utils.hasValue(descriptionText)) {
            mNoticeDialogComponentView.setDescriptionText(descriptionText)
        }
        if (Utils.hasValue(acceptText)) {
            mNoticeDialogComponentView.setAcceptTextButton(acceptText, onAcceptClickListener)
        }
        if (Utils.hasValue(denyText)) {
            mNoticeDialogComponentView.setDenyTextButton(denyText, onDenyClickListener)
        }
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

        val LAYOUT_ID = R.layout.notice_dialog_fragment

        fun newInstance(bundle: Bundle?) = NoticeDialogFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}

