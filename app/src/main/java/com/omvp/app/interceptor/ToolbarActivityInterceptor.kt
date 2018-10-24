package com.omvp.app.interceptor

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.raxdenstudios.square.interceptor.ActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback


class ToolbarActivityInterceptor : ActivityInterceptor<ToolbarInterceptorCallback>, ToolbarInterceptor {

    constructor(activity: FragmentActivity) : super(activity)

    constructor(activity: FragmentActivity, callback: ToolbarInterceptorCallback) : super(activity, callback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar: Toolbar = mCallback.onCreateToolbarView(savedInstanceState)
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
