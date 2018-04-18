package com.omvp.app.util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.omvp.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import timber.log.Timber;

public class SocialAuthManager {

    public static final int REQUEST_CODE_GOOGLE_LOGIN = 1023;

    public enum SocialAuth {ALL, GOOGLE, FACEBOOK, TWITTER}

    private Activity mActivity;
    private Resources mResources;

    private SocialAuthCallback mCallback;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private SocialAuth mSocialAuth;

    public static class SocialAuthData {
        public String token;
        public String email;
        public String name;
        public String photo;
    }

    public interface SocialAuthCallback {
        void onSignInSuccess(SocialAuth socialAuth, SocialAuthData data);

        void onSignInFailed(SocialAuth socialAuth, String message);
    }

    public SocialAuthManager(Activity activity, GoogleSignInClient googleSignInClient) {
        mActivity = activity;
        mResources = activity.getResources();
        mGoogleSignInClient = googleSignInClient;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (mSocialAuth) {
            case GOOGLE:
                onGoogleActivityResult(requestCode, resultCode, data);
                break;
            case FACEBOOK:
                onFacebookActivityResult(requestCode, resultCode, data);
                break;
            default:
                onFacebookActivityResult(requestCode, resultCode, data);
                onGoogleActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void init(SocialAuth socialAuth, SocialAuthCallback callback) {
        init(socialAuth);
        mCallback = callback;
    }

    public void init(SocialAuthCallback callback) {
        init(SocialAuth.ALL);
        mCallback = callback;
    }

    public void destroy() {
        logout();
        switch (mSocialAuth) {
            case GOOGLE:
                break;
            case FACEBOOK:
                destroyFacebook();
                break;
            case TWITTER:
                // TODO: 05/04/2018
                break;
            default:
                break;
        }
    }

    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_LOGIN);
    }

    public void signInWithFacebook() {
        LoginManager.getInstance()
                .logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "email"));
    }

    public void logout() {
        switch (mSocialAuth) {
            case FACEBOOK:
                LoginManager.getInstance().logOut();
                break;
            case GOOGLE:
                mGoogleSignInClient.signOut();
                break;
            case TWITTER:
                // TODO: 05/04/2018
                break;
            case ALL:
                LoginManager.getInstance().logOut();
                mGoogleSignInClient.signOut();
                break;
        }
    }

    // private methods

    private void init(SocialAuth socialAuth) {
        mSocialAuth = socialAuth;
        switch (mSocialAuth) {
            case GOOGLE:
                break;
            case FACEBOOK:
                initFacebook();
                break;
            case TWITTER:
                // TODO: 05/04/2018
                break;
            default:
                initFacebook();
                break;
        }
    }

    private void onGoogleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() && result.getSignInAccount() != null) {
                GoogleSignInAccount account = result.getSignInAccount();
                SocialAuthData socialAuthData = new SocialAuthData();
                socialAuthData.token = account.getIdToken();
                socialAuthData.email = account.getEmail();
                socialAuthData.name = account.getDisplayName();
                if (account.getPhotoUrl() != null) {
                    socialAuthData.photo = account.getPhotoUrl().toString();
                }
                onGoogleSignInSuccess(socialAuthData);
            } else {
                onGoogleSignInFailed(mResources.getString(R.string.google_signin_error));
            }
        }
    }

    private void onGoogleSignInSuccess(SocialAuthData data) {
        if (mCallback != null) {
            mCallback.onSignInSuccess(SocialAuth.GOOGLE, data);
        }
    }

    private void onFacebookSignInSuccess(SocialAuthData data) {
        if (mCallback != null) {
            mCallback.onSignInSuccess(SocialAuth.FACEBOOK, data);
        }
    }

    private void onGoogleSignInFailed(String reason) {
        if (mCallback != null) {
            mCallback.onSignInFailed(SocialAuth.GOOGLE, reason);
        }
    }

    private void onFacebookSignInFailed(String reason) {
        if (mCallback != null) {
            mCallback.onSignInFailed(SocialAuth.FACEBOOK, reason);
        }
    }

    private void destroyFacebook() {
        if (mCallbackManager != null) {
            LoginManager.getInstance().unregisterCallback(mCallbackManager);
            mCallbackManager = null;
        }
    }

    // Facebook Authorization
    private void initFacebook() {
        if (mCallbackManager == null) {
            mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Timber.d("SignWithFacebook: onSuccess: %s", loginResult.toString());

                    final AccessToken accessToken = loginResult.getAccessToken();
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            SocialAuthData socialAuthData = new SocialAuthData();
                            socialAuthData.token = accessToken.getToken();
                            if (object != null) {
                                try {
                                    if (object.has("email")) {
                                        socialAuthData.email = object.getString("email");
                                    }
                                    if (object.has("name")) {
                                        socialAuthData.name = object.getString("name");
                                    }
                                    if (object.has("picture")) {
                                        JSONObject jsonPicture = object.getJSONObject("picture");
                                        if (jsonPicture != null && jsonPicture.has("data")) {
                                            JSONObject jsonData = jsonPicture.getJSONObject("data");
                                            if (jsonData != null && jsonData.has("url")) {
                                                socialAuthData.photo = jsonData.getString("url");
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    Timber.e(e, e.getMessage());
                                }
                            }
                            onFacebookSignInSuccess(socialAuthData);
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, email, name, picture.type(large)");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    Timber.d("SignWithFacebook: onCancel");
                }

                @Override
                public void onError(FacebookException exception) {
                    Timber.d("SignWithFacebook: onError: %s", exception.getMessage());
                    if (exception instanceof FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                            signInWithFacebook();
                        }
                    } else {
                        onFacebookSignInFailed(mResources.getString(R.string.facebook_signin_error));
                    }
                }
            });
        }
    }

    private void onFacebookActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
