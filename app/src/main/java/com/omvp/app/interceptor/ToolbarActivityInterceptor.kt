package com.omvp.app.interceptor

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import com.raxdenstudios.square.interceptor.ActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback


class ToolbarActivityInterceptor : ActivityInterceptor<ToolbarInterceptorCallback>, ToolbarInterceptor {

    constructor(activity: Activity) : super(activity) {}

    constructor(activity: Activity, callback: ToolbarInterceptorCallback) : super(activity, callback) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = mCallback.onCreateToolbarView(savedInstanceState)
        if (toolbar != null) {
            if (mActivity is AppCompatActivity) {
                val compatActivity = mActivity as AppCompatActivity
                compatActivity.setSupportActionBar(toolbar)
                val actionBar = compatActivity.supportActionBar
                actionBar?.setDisplayShowTitleEnabled(false)
            }
            toolbar.setOnMenuItemClickListener { item -> mActivity.onOptionsItemSelected(item) }
            toolbar.setNavigationOnClickListener { mActivity.onBackPressed() }
            mCallback.onToolbarViewCreated(toolbar)
        }
    }
}
