package com.omvp.app

import com.crashlytics.android.Crashlytics
import com.facebook.appevents.AppEventsLogger
import com.jakewharton.threetenabp.AndroidThreeTen
import com.omvp.app.base.BaseApplication
import com.omvp.app.util.CrashReportingTree
import com.omvp.commons.Constants

import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Ángel Gómez on 16/02/2018.
 */
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        initFabric()
        initTimber()
        initCalligraphy()
        initTreeTen()
        initFacebook()
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics())
    }

    private fun initTimber() {
        val tree: Timber.Tree = if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            CrashReportingTree()
        }
        Timber.plant(tree)
    }

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(Constants.DEFAULT_FONT)
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun initTreeTen() {
        AndroidThreeTen.init(this)
    }

    private fun initFacebook() {
        AppEventsLogger.activateApp(this)
    }
}
