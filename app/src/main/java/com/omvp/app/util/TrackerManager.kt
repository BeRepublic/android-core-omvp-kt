package com.omvp.app.util

import android.support.v4.app.Fragment
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import com.raxdenstudios.commons.util.Utils
import timber.log.Timber
import java.util.*

class TrackerManager(private val mTracker: Tracker?,
                     private val mFirebaseAnalytics: FirebaseAnalytics) {

    private val mScreenNames = HashMap<String, String>()

    init {
        initScreenNames()
    }

    fun trackScreen(fragment: Fragment) {
        val fragmentName = fragment.javaClass.name
        if (mScreenNames.containsKey(fragmentName)) {
            val screenName = mScreenNames[fragmentName]
            if (Utils.hasValue(screenName)) {
                trackScreen(screenName)
            }
        }
    }

    fun trackScreen(screenName: String?) {
        Timber.d("trackScreen: %s", screenName ?: "")
        if (mTracker != null) {
            mTracker.setScreenName(screenName ?: "")
            mTracker.send(HitBuilders.ScreenViewBuilder().build())
        }
    }

    fun trackEvent(category: String, action: String, label: String) {
        Timber.d("TrackEvent - Category: %s , Action: %s, Label: %s", category, action, label)
        mTracker?.send(HitBuilders.EventBuilder()
                .setAction(action)
                .setCategory(category)
                .setLabel(label)
                .build())
    }

    private fun initScreenNames() {
        //        mScreenNames.put(SplashFragment.class.getName(), "sample");
    }

}
