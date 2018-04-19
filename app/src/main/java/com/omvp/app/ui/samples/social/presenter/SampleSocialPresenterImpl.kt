package com.omvp.app.ui.samples.social.presenter

import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.social.view.SampleSocialView
import com.omvp.app.util.SocialAuthManager
import javax.inject.Inject

class SampleSocialPresenterImpl
@Inject
internal constructor(sampleSocialView: SampleSocialView) : BasePresenter<SampleSocialView>(sampleSocialView),
        SampleSocialPresenter, SocialAuthManager.SocialAuthCallback {

    @Inject
    internal lateinit var mSocialAuthManager: SocialAuthManager

    internal var mSocialAuth: SocialAuthManager.SocialAuth? = null

    internal var mSocialAuthData: SocialAuthManager.SocialAuthData? = null

    override fun onViewLoaded() {
        super.onViewLoaded()
    }

    override fun onSignInSuccess(socialAuth: SocialAuthManager.SocialAuth, socialAuthData: SocialAuthManager.SocialAuthData) {
        mSocialAuth = socialAuth
        mSocialAuthData = socialAuthData
        when (mSocialAuth) {
            SocialAuthManager.SocialAuth.FACEBOOK -> signInWithFacebook(socialAuthData.token)
            SocialAuthManager.SocialAuth.GOOGLE -> signInWithGoogle(socialAuthData.token)
        }
        drawData(mSocialAuthData!!)
        showHideSocialButtons(false)
    }

    override fun onSignInFailed(socialAuth: SocialAuthManager.SocialAuth, message: String) {

    }

    override fun continueWithFacebook() {
        mSocialAuthManager.signInWithFacebook()
    }

    override fun continueWithGoogle() {
        mSocialAuthManager.signInWithGoogle()
    }

    override fun logout() {
        mSocialAuthManager.logout()
        drawData(SocialAuthManager.SocialAuthData("", "", "", ""))
        showHideSocialButtons(true)
    }

    private fun signInWithGoogle(token: String) {
        // TODO: 04/04/2018
    }

    private fun signInWithFacebook(token: String) {
        // TODO: 04/04/2018
    }

    private fun drawData(data: SocialAuthManager.SocialAuthData) {
        mView?.drawImage(data.photo)
        mView?.drawName(data.name)
        mView?.drawEmail(data.email)
    }

    private fun showHideSocialButtons(show: Boolean) {
        mView?.showHideSocialButtons(show)
    }

}
