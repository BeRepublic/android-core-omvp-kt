package com.omvp.app.ui.splash.view

import android.os.Bundle
import com.omvp.app.AppFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.splash.presenter.SplashPresenter

class SplashFragment : AppFragment<SplashPresenter, SplashFragment.FragmentCallback>(), SplashView {

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
