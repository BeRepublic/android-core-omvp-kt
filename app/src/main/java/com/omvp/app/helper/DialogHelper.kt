package com.omvp.app.helper

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omvp.app.dialog.notice.view.NoticeDialogFragment
import timber.log.Timber

class DialogHelper {

    private var mResources: Resources
    private var mExtras: Bundle?
    private val mFragmentManager: FragmentManager

    constructor(activity: Activity, fragmentManager: FragmentManager) {
        mResources = activity.resources
        mFragmentManager = fragmentManager
        mExtras = activity.intent.extras
    }

    constructor(fragment: Fragment, fragmentManager: FragmentManager){
        mResources = fragment.resources
        mFragmentManager = fragmentManager
        mExtras = fragment.arguments
    }

    fun showMessage(title: String?, message: String?, onAcceptClickListener: View.OnClickListener? = null): NoticeDialogFragment {
        val dialog = NoticeDialogFragment.newInstance(mExtras)
        dialog.setTitle(title ?: "")
        dialog.setDescription(message ?: "")
        dialog.setAcceptButton(mResources.getString(android.R.string.ok), View.OnClickListener { v ->
            onAcceptClickListener?.onClick(v)
            dialog.dismiss()
        })
        showDialog(dialog, "showMessage")
        return dialog
    }

    fun showError(title: String?, message: String?, onAcceptClickListener: View.OnClickListener? = null): NoticeDialogFragment {
        val dialog = NoticeDialogFragment.newInstance(mExtras)
        dialog.setTitle(title ?: "")
        dialog.setDescription(message ?: "")
        dialog.setAcceptButton(mResources.getString(android.R.string.ok), View.OnClickListener { v ->
            onAcceptClickListener?.onClick(v)
            dialog.dismiss()
        })
        showDialog(dialog, "showError")
        return dialog
    }

    private fun showDialog(dialog: DialogFragment, tag: String) {
        try {
            dialog.show(mFragmentManager, tag)
        } catch (e: IllegalStateException) {
            Timber.e(e)
        }

    }
}

