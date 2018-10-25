package com.omvp.app.interceptor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
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
