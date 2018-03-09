package com.omvp.app.ui.home

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.home.view.HomeFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject


class HomeActivity :
        BaseFragmentActivity(),
        ToolbarInterceptorCallback,
        InjectFragmentInterceptorCallback<HomeFragment>,
        HomeFragment.FragmentCallback {

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

    override fun onSampleCameraSelected() {
        mNavigationHelper.launchSampleTakePicture()
    }

}
