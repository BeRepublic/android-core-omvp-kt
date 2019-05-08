package com.omvp.app.base.mvp.view

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.omvp.app.R
import com.omvp.app.base.mvp.presenter.Presenter

open class BaseViewFragmentDialog<TPresenter : Presenter, TCallback : BaseViewFragmentCallback> : BaseViewFragment<TPresenter, TCallback>() {

    private var mOnDismissListener: MutableList<DialogInterface.OnDismissListener> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = super.onCreateDialog(savedInstanceState).apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        mOnDismissListener.clear()
        mOnDismissListener = mutableListOf()
    }

    fun addOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        mOnDismissListener.add(onDismissListener)
    }

    fun removeOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        mOnDismissListener.remove(onDismissListener)
    }

}
