package com.omvp.app.ui.splash.view

import android.os.Bundle

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.splash.presenter.SplashPresenter

class SplashFragment : BaseViewFragment<SplashPresenter, SplashFragment.FragmentCallback>(), SplashView {

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onLaunchApplication()
    }

    override fun applicationReadyToLaunch() {
        mCallback.onLaunchApplication()
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SplashFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

}
