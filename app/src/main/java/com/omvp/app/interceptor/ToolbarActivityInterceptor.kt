package com.omvp.app.interceptor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.raxdenstudios.square.interceptor.ActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback


class ToolbarActivityInterceptor : ActivityInterceptor<ToolbarInterceptorCallback>, ToolbarInterceptor {

    constructor(activity: FragmentActivity) : super(activity)

    constructor(activity: FragmentActivity, callback: ToolbarInterceptorCallback) : super(activity, callback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = callback?.onCreateToolbarView(savedInstanceState)
        if (toolbar != null) {
            if (activity is AppCompatActivity) {
                val compatActivity = activity as AppCompatActivity
                compatActivity.setSupportActionBar(toolbar)
                val actionBar = compatActivity.supportActionBar
                actionBar?.setDisplayShowTitleEnabled(false)
            }
            toolbar.setOnMenuItemClickListener { activity.onOptionsItemSelected(it) }
            toolbar.setNavigationOnClickListener { activity.onBackPressed() }
            callback?.onToolbarViewCreated(toolbar)
        }
    }
}
