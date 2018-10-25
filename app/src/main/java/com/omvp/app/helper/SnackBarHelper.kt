package com.omvp.app.helper

import android.app.Activity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

import com.omvp.app.R

class SnackBarHelper(private val mActivity: Activity) {
    fun showNetworkError() {
        val view = mActivity.findViewById<View>(R.id.coordinator_layout_view)
        if (view is CoordinatorLayout) {
            Snackbar.make(view, mActivity.resources.getString(R.string.network_unavailable), Snackbar.LENGTH_LONG).show()
        }
    }
}
