package com.omvp.app.ui.splash

import android.os.Bundle
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.splash.view.SplashFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback

import javax.inject.Inject

class SplashActivity :
        BaseFragmentActivity(),
        SplashFragment.FragmentCallback,
        InjectFragmentInterceptorCallback<SplashFragment> {

    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mFragment: SplashFragment

    // =============== SplashFragment.FragmentCallback =============================================

    override fun onLaunchApplication() {
        mNavigationHelper.launchHomeAndFinishPreviousViews()
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): SplashFragment {
        return SplashFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SplashFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mInjectFragmentInterceptor)
    }

}
