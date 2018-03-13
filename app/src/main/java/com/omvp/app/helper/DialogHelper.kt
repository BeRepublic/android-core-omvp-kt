package com.omvp.app.helper

import android.app.Activity
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.res.Resources
import android.os.Bundle

import timber.log.Timber

class DialogHelper(
        private val mActivity: Activity,
        private val mFragmentManager: FragmentManager
) {
    private val mResources: Resources = mActivity.resources
    private val mExtras: Bundle? = mActivity.intent.extras

    private fun showDialog(dialog: DialogFragment, tag: String) {
        try {
            dialog.show(mFragmentManager, tag)
        } catch (e: IllegalStateException) {
            Timber.e(e.message, e)
        }
    }
}
