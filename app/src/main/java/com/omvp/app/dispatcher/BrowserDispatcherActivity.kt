package com.omvp.app.dispatcher

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import com.omvp.app.base.BaseActivity

/**
 * ${rootProject.ext.androidAppName}://open?action=operation_1
 * ${rootProject.ext.androidAppName}://open?action=operation_2
 */
class BrowserDispatcherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            val action = uri?.getQueryParameter(ACTION)
            // TODO: depends of server implementation
        }
    }

    companion object {

        private val ACTION = "action"
    }

}
