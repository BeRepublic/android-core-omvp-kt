
package com.omvp.app.ui.samples.social.presenter;


import com.omvp.app.base.mvp.presenter.BasePresenter;
import com.omvp.app.ui.samples.social.view.SampleSocialView;
import com.omvp.app.util.SocialAuthManager;

import javax.inject.Inject;

public class SampleSocialPresenterImpl extends BasePresenter<SampleSocialView>
        implements SampleSocialPresenter, SocialAuthManager.SocialAuthCallback {

    @Inject
    SocialAuthManager mSocialAuthManager;

    private SocialAuthManager.SocialAuth mSocialAuth;
    private SocialAuthManager.SocialAuthData mSocialAuthData;

    @Inject
    public SampleSocialPresenterImpl(SampleSocialView sampleSocialView) {
        super(sampleSocialView);
    }

    @Override
    public void onViewLoaded() {
        super.onViewLoaded();
    }

    @Override
    public void onSignInSuccess(SocialAuthManager.SocialAuth socialAuth, SocialAuthManager.SocialAuthData socialAuthData) {
        mSocialAuth = socialAuth;
        mSocialAuthData = socialAuthData;

        switch (mSocialAuth) {
            case FACEBOOK:
                signInWithFacebook(socialAuthData.token);
                break;
            case GOOGLE:
                signInWithGoogle(socialAuthData.token);
                break;
        }

        drawData(mSocialAuthData);
        showHideSocialButtons(false);
    }

    @Override
    public void onSignInFailed(SocialAuthManager.SocialAuth socialAuth, String message) {

    }

    @Override
    public void continueWithFacebook() {
        mSocialAuthManager.signInWithFacebook();
    }

    @Override
    public void continueWithGoogle() {
        mSocialAuthManager.signInWithGoogle();
    }

    @Override
    public void logout() {
        mSocialAuthManager.logout();
        SocialAuthManager.SocialAuthData data = new SocialAuthManager.SocialAuthData();
        data.email = "";
        data.name = "";
        data.photo = "";
        data.token = "";
        drawData(data);
        showHideSocialButtons(true);
    }

    private void signInWithGoogle(String token) {
        // TODO: 04/04/2018
    }

    private void signInWithFacebook(String token) {
        // TODO: 04/04/2018
    }

    private void drawData(SocialAuthManager.SocialAuthData data) {
        if (getMView() != null) {
            getMView().drawImage(data.photo);
            getMView().drawName(data.name);
            getMView().drawEmail(data.email);
        }
    }

    private void showHideSocialButtons(boolean show) {
        if (getMView() != null) {
            getMView().showHideSocialButtons(show);
        }
    }
}
