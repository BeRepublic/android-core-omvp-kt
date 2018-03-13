package com.omvp.app.injector.module

import android.app.Application

import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import com.omvp.app.R

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
object AnalyticsModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun firebaseAnalytics(application: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(application)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun tracker(application: Application): Tracker {
        val analytics = GoogleAnalytics.getInstance(application)
        return analytics.newTracker(R.xml.app_tracker)
    }

}
