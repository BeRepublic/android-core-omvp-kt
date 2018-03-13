package com.omvp.app.helper

import android.app.Activity
import android.os.Bundle

import com.omvp.app.ui.home.HomeActivity
import com.omvp.app.ui.samples.sample.SampleActivity
import com.omvp.app.ui.samples.samplelist.SampleListActivity
import com.omvp.app.ui.samples.samplelocation.SampleLocationActivity
import com.omvp.app.ui.samples.sampletakepicture.SampleTakePictureActivity
import com.omvp.app.ui.samples.samplemultiple.SampleMultipleActivity
import com.omvp.app.ui.samples.samplepager.SamplePagerActivity
import com.omvp.app.ui.splash.SplashActivity
import com.omvp.app.util.OperationBroadcastManager
import com.raxdenstudios.commons.manager.NavigationManager

class NavigationHelper(private val mActivity: Activity) {

    private val extras: Bundle by lazy {
        if (mActivity.intent != null && mActivity.intent.extras != null)
            mActivity.intent.extras else Bundle()
    }

    fun launchSplash() {
        OperationBroadcastManager.finishAllActivities(mActivity)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SplashActivity::class.java)
                .launch()
    }

    fun launchHomeAndFinishPreviousViews() {
        OperationBroadcastManager.finishAllActivities(mActivity)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(HomeActivity::class.java)
                .launch()
    }

    fun launchSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleActivity::class.java)
                .launch()
    }

    fun launchSample(sampleItemId: Long) {
        val extras = extras
        extras.putLong(Long::class.java.simpleName, sampleItemId)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleActivity::class.java)
                .launch()
    }

    fun launchSampleList() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleListActivity::class.java)
                .launch()
    }

    fun launchSamplePager() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SamplePagerActivity::class.java)
                .launch()
    }

    fun launchSampleMap() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleMultipleActivity::class.java)
                .launch()
    }

    fun launchSampleLocation() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleLocationActivity::class.java)
                .launch()
    }


    fun launchSampleTakePicture() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleTakePictureActivity::class.java)
                .launch()
    }


}
