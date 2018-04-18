package com.omvp.app.ui.samples.social.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.omvp.app.R;
import com.omvp.app.base.mvp.view.BaseViewFragment;
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback;
import com.omvp.app.ui.samples.social.presenter.SampleSocialPresenterImpl;
import com.omvp.app.util.ImageHelper;
import com.omvp.app.util.SocialAuthManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SampleSocialFragment extends BaseViewFragment<SampleSocialPresenterImpl, SampleSocialFragment.FragmentCallback>
        implements SampleSocialView {

    @BindView(R.id.user_image)
    AppCompatImageView mUserImageView;
    @BindView(R.id.user_name)
    AppCompatTextView mUserNameView;
    @BindView(R.id.user_email)
    AppCompatTextView mUserEmailView;
    @BindView(R.id.social_container)
    View mSocialButtonsContainerView;
    @BindView(R.id.logout)
    Button mLogoutButton;

    @Inject
    SocialAuthManager mSocialAuthManager;

    public interface FragmentCallback extends BaseViewFragmentCallback {

    }

    public static SampleSocialFragment newInstance(Bundle bundle) {
        SampleSocialFragment fragment = new SampleSocialFragment();
        bundle = bundle == null ? new Bundle() : bundle;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocialAuthManager.init(mPresenter);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        setupViews();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSocialAuthManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        mSocialAuthManager.destroy();
        super.onDestroy();
    }

    @OnClick(R.id.facebook)
    public void onFacebookLoginClicked(View view) {
        mPresenter.continueWithFacebook();
    }

    @OnClick(R.id.google)
    public void onGoogleLoginClicked(View view) {
        mPresenter.continueWithGoogle();
    }

    @OnClick(R.id.logout)
    public void onLogoutClicked(View view) {
        mPresenter.logout();
    }

    @Override
    public void drawImage(String imageUrl) {
        ImageHelper.loadImageUser(mContext, imageUrl, mUserImageView, null);
    }

    @Override
    public void drawName(String name) {
        mUserNameView.setText(name);
    }

    @Override
    public void drawEmail(String email) {
        mUserEmailView.setText(email);
    }

    @Override
    public void showHideSocialButtons(boolean show) {
        mSocialButtonsContainerView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mLogoutButton.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }


    private void setupViews() {

    }
}
