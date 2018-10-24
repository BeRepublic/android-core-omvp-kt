package com.omvp.app.helper

import android.app.Activity
import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.omvp.app.interceptor.operation.OperationBroadcastActivityInterceptor
import com.omvp.app.ui.samples.bottomnavigation.BottomNavigationActivity
import com.omvp.app.ui.samples.detail.SampleDetailActivity
import com.omvp.app.ui.samples.home.HomeActivity
import com.omvp.app.ui.samples.inputs.SampleInputActivity
import com.omvp.app.ui.samples.list.SampleListActivity
import com.omvp.app.ui.samples.listhorizontal.SampleListHorizontalActivity
import com.omvp.app.ui.samples.locale.SampleLocaleActivity
import com.omvp.app.ui.samples.location.SampleLocationActivity
import com.omvp.app.ui.samples.multiple.SampleMultipleActivity
import com.omvp.app.ui.samples.noticedialog.SampleNoticeActivity
import com.omvp.app.ui.samples.pager.SamplePagerActivity
import com.omvp.app.ui.samples.requestphone.SampleRequestPhoneActivity
import com.omvp.app.ui.samples.simple.SampleActivity
import com.omvp.app.ui.samples.social.SampleSocialActivity
import com.omvp.app.ui.samples.takepicture.SampleTakePictureActivity
import com.omvp.app.ui.samples.vibration.VibrationActivity
import com.omvp.app.ui.splash.SplashActivity
import com.raxdenstudios.navigation.NavigationManager

class NavigationHelper(private val mActivity: Activity) {

    private val extras: Bundle by lazy {
        mActivity.intent?.extras ?: Bundle()
    }

    fun launchSplash() {
        OperationBroadcastActivityInterceptor.finishAllActivities(mActivity)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SplashActivity::class)
                .launch()
    }

    fun launchHomeAndFinishPreviousViews() {
        OperationBroadcastActivityInterceptor.finishAllActivities(mActivity)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(HomeActivity::class)
                .launch()
    }

    fun launchSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleActivity::class)
                .launch()
    }

    fun launchSample(sampleItemId: String) {
        val extras = extras
        extras.putString(String::class.java.simpleName, sampleItemId)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleActivity::class)
                .launch()
    }

    fun launchDetail(sampleItemId: String) {
        val extras = extras
        extras.putString(String::class.java.simpleName, sampleItemId)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleDetailActivity::class)
                .launch()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun launchDetailWithSharedViewTransition(sampleItemId: String, sharedView: View) {
        val extras = extras
        extras.putString(String::class.java.simpleName, sampleItemId)

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                mActivity, sharedView, "item")

        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleDetailActivity::class, activityOptions.toBundle())
                .launch()
    }

    fun launchSampleList() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleListActivity::class)
                .launch()
    }

    fun launchSampleHorizontalList() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleListHorizontalActivity::class)
                .launch()
    }

    fun launchSamplePager() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SamplePagerActivity::class)
                .launch()
    }

    fun launchSampleMap() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleMultipleActivity::class)
                .launch()
    }

    fun launchSampleLocation() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleLocationActivity::class)
                .launch()
    }


    fun launchSampleTakePicture() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleTakePictureActivity::class)
                .launch()
    }

    fun launchSampleLocale() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleLocaleActivity::class)
                .launch()
    }

    fun launchVibrationSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(VibrationActivity::class)
                .launch()
    }

    fun launchInputViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleInputActivity::class)
                .launch()
    }

    fun launchSocialViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleSocialActivity::class)
                .launch()
    }

    fun launchNoticeDialogViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleNoticeActivity::class)
                .launch()
    }

    fun launchBottomBarSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(BottomNavigationActivity::class)
                .launch()
    }

    fun launchAuthPhoneView() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateToKClass(SampleRequestPhoneActivity::class)
                .launch()
    }
}
