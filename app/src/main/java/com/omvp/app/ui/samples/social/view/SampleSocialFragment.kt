package com.omvp.app.ui.samples.social.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.social.presenter.SampleSocialPresenterImpl
import com.omvp.app.util.SocialAuthManager
import javax.inject.Inject

class SampleSocialFragment : BaseViewFragment<SampleSocialPresenterImpl, SampleSocialFragment.FragmentCallback>(), SampleSocialView
{

//    internal lateinit var mUserImageView: AppCompatImageView
//    internal lateinit var mUserNameView: AppCompatTextView
//    internal lateinit var mUserEmailView: AppCompatTextView
//    internal lateinit var mSocialButtonsContainerView: View
//    internal lateinit var mLogoutButton: Button

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


    fun onFacebookLoginClicked() {
        mPresenter.continueWithFacebook()
    }

    fun onGoogleLoginClicked() {
        mPresenter.continueWithGoogle()
    }

    fun onLogoutClicked() {
        mPresenter.logout()
    }

    override fun drawImage(imageUrl: String) {
//        ImageHelper.loadImageUser(mContext, imageUrl, mUserImageView as ImageView, null)
    }

    override fun drawName(name: String) {
//        mUserNameView!!.text = name
    }

    override fun drawEmail(email: String) {
//        mUserEmailView!!.text = email
    }

    override fun showHideSocialButtons(show: Boolean) {
//        mSocialButtonsContainerView!!.visibility = if (show) View.VISIBLE else View.INVISIBLE
//        mLogoutButton!!.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }
}
