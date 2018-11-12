package com.omvp.app.ui.samples.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.home.view.HomeFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject


class HomeActivity : BaseFragmentActivity(), ToolbarInterceptorCallback,
        InjectFragmentInterceptorCallback<HomeFragment>, HomeFragment.FragmentCallback {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: HomeFragment

    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): HomeFragment {
        return HomeFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: HomeFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
    }

    override fun onSampleViewSelected() {
        mNavigationHelper.launchSample()
    }

    override fun onSampleListSelected() {
        mNavigationHelper.launchSampleList()
    }

    override fun onSamplePagerSelected() {
        mNavigationHelper.launchSamplePager()
    }

    override fun onSampleMultipleSelected() {
        mNavigationHelper.launchSampleMap()
    }

    override fun onSampleLocationSelected() {
        mNavigationHelper.launchSampleLocation()
    }

    override fun onSampleTakePictureSelected() {
        mNavigationHelper.launchSampleTakePicture()
    }

    override fun onSampleLocaleSelected() {
        mNavigationHelper.launchSampleLocale()
    }

    override fun onSampleHorizontalListClicked() {
        mNavigationHelper.launchSampleHorizontalList()
    }

    override fun onVibrationSelected() {
        mNavigationHelper.launchVibrationSample()
    }

    override fun onInputViewSelected() {
        mNavigationHelper.launchInputViewSample()
    }

    override fun onSocialViewSelected() {
        mNavigationHelper.launchSocialViewSample()
    }

    override fun onNoticeDialogViewSelected() {
        mNavigationHelper.launchNoticeDialogViewSample()
    }

    override fun onBottomNavigationViewSelected() {
        mNavigationHelper.launchBottomBarSample()
    }

    override fun onAuthPhoneViewSelected() {
        mNavigationHelper.launchAuthPhoneView()
    }

    override fun onCoroutineSampleViewSelected() {
        mNavigationHelper.launchCoroutineSampleView()
    }
}
