package com.omvp.app.ui.samples.social.view

import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnClick
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.social.presenter.SampleSocialPresenterImpl
import com.omvp.app.util.ImageHelper
import com.omvp.app.util.SocialAuthManager
import javax.inject.Inject

class SampleSocialFragment : BaseViewFragment<SampleSocialPresenterImpl, SampleSocialFragment.FragmentCallback>(), SampleSocialView
{

    @BindView(R.id.user_image)
    internal lateinit var mUserImageView: AppCompatImageView
    @BindView(R.id.user_name)
    internal lateinit var mUserNameView: AppCompatTextView
    @BindView(R.id.user_email)
    internal lateinit var mUserEmailView: AppCompatTextView
    @BindView(R.id.social_container)
    internal lateinit var mSocialButtonsContainerView: View
    @BindView(R.id.logout)
    internal lateinit var mLogoutButton: Button

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
        mSocialAuthManager!!.init(mPresenter)
    }

    override fun onDestroy() {
        mSocialAuthManager!!.destroy()
        super.onDestroy()
    }


    @OnClick(R.id.facebook)
    fun onFacebookLoginClicked(view: View) {
        mPresenter.continueWithFacebook()
    }

    @OnClick(R.id.google)
    fun onGoogleLoginClicked(view: View) {
        mPresenter.continueWithGoogle()
    }

    @OnClick(R.id.logout)
    fun onLogoutClicked(view: View) {
        mPresenter.logout()
    }

    override fun drawImage(imageUrl: String) {
        ImageHelper.loadImageUser(mContext, imageUrl, mUserImageView as ImageView, null)
    }

    override fun drawName(name: String) {
        mUserNameView!!.text = name
    }

    override fun drawEmail(email: String) {
        mUserEmailView!!.text = email
    }

    override fun showHideSocialButtons(show: Boolean) {
        mSocialButtonsContainerView!!.visibility = if (show) View.VISIBLE else View.INVISIBLE
        mLogoutButton!!.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }
}
