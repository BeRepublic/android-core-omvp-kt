package com.omvp.app.util

import android.util.Log

import com.crashlytics.android.Crashlytics

import timber.log.Timber

/**
 * Created by agomez on 10/03/2016.
 */
class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        if (messageCondition(message)) {
            return
        }
        Crashlytics.logException(t)
    }

    private fun messageCondition(message: String): Boolean {
        return (message.contains("java.net.UnknownHostException")
                || message.contains("java.net.ConnectException")
                || message.contains("java.net.SocketTimeoutException"))
    }

}
