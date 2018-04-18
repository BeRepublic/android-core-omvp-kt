package com.omvp.app.helper

import android.app.Activity
import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.omvp.app.interceptor.operation.OperationBroadcastActivityInterceptor
import com.omvp.app.ui.samples.detail.SampleDetailActivity
import com.omvp.app.ui.samples.home.HomeActivity
import com.omvp.app.ui.samples.inputs.SampleInputActivity
import com.omvp.app.ui.samples.list.SampleListActivity
import com.omvp.app.ui.samples.listhorizontal.SampleListHorizontalActivity
import com.omvp.app.ui.samples.locale.SampleLocaleActivity
import com.omvp.app.ui.samples.location.SampleLocationActivity
import com.omvp.app.ui.samples.multiple.SampleMultipleActivity
import com.omvp.app.ui.samples.notice_dialog.SampleNoticeActivity
import com.omvp.app.ui.samples.pager.SamplePagerActivity
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
                .navigateTo(SplashActivity::class.java)
                .launch()
    }

    fun launchHomeAndFinishPreviousViews() {
        OperationBroadcastActivityInterceptor.finishAllActivities(mActivity)
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

    fun launchSample(sampleItemId: String) {
        val extras = extras
        extras.putString(String::class.java.simpleName, sampleItemId)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleActivity::class.java)
                .launch()
    }

    fun launchDetail(sampleItemId: String) {
        val extras = extras
        extras.putString(String::class.java.simpleName, sampleItemId)
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleDetailActivity::class.java)
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
                .navigateTo(SampleDetailActivity::class.java, activityOptions.toBundle())
                .launch()
    }

    fun launchSampleList() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleListActivity::class.java)
                .launch()
    }

    fun launchSampleHorizontalList() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleListHorizontalActivity::class.java)
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

    fun launchSampleLocale() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleLocaleActivity::class.java)
                .launch()
    }

    fun launchVibrationSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(VibrationActivity::class.java)
                .launch()
    }

    fun launchInputViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleInputActivity::class.java)
                .launch()
    }

    fun launchSocialViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleSocialActivity::class.java)
                .launch()
    }

    fun launchNoticeDialogViewSample() {
        NavigationManager.Builder(mActivity)
                .putData(extras)
                .navigateTo(SampleNoticeActivity::class.java)
                .launch()
    }

    fun launchBottomBarSample() {
//        NavigationManager.Builder(mActivity)
//                .putData(getExtras())
//                .navigateTo(BottomNavigationActivity::class.java)
//                .launch()
    }

    fun launchAuthPhoneView() {
//        NavigationManager.Builder(mActivity)
//                .putData(getExtras())
//                .navigateTo(SampleRequestPhoneActivity::class.java)
//                .launch()
    }
}
