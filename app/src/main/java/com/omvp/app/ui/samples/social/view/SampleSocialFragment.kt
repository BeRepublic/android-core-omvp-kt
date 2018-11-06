package com.omvp.app.ui.samples.social.view

import android.os.Bundle
import android.view.View
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.social.presenter.SampleSocialPresenterImpl
import com.omvp.app.util.ImageHelper
import com.omvp.app.util.SocialAuthManager
import kotlinx.android.synthetic.main.sample_social_fragment.*
import javax.inject.Inject

class SampleSocialFragment : BaseViewFragment<SampleSocialPresenterImpl, SampleSocialFragment.FragmentCallback>(), SampleSocialView {

    @Inject
    internal lateinit var mSocialAuthManager: SocialAuthManager

    interface FragmentCallback : BaseViewFragmentCallback

    companion object {

        fun newInstance(bundle: Bundle?) = SampleSocialFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSocialAuthManager.init(mPresenter)
    }

    override fun onDestroy() {
        mSocialAuthManager.destroy()
        super.onDestroy()
    }

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)
        facebook.setOnClickListener { mPresenter.continueWithFacebook() }
        google.setOnClickListener { mPresenter.continueWithGoogle() }
        logout.setOnClickListener { mPresenter.logout() }
    }

    override fun drawImage(imageUrl: String) {
        ImageHelper.loadImageUser(mContext, imageUrl, user_image, null)
    }

    override fun drawName(name: String) {
        user_name.text = name
    }

    override fun drawEmail(email: String) {
        user_email.text = email
    }

    override fun showHideSocialButtons(show: Boolean) {
        social_container.visibility = if (show) View.VISIBLE else View.INVISIBLE
        logout.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }
}
