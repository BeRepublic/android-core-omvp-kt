package com.omvp.app.util

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.omvp.app.R
import org.json.JSONException
import timber.log.Timber
import java.util.*

class SocialAuthManager(private val mActivity: Activity, private val mGoogleSignInClient: GoogleSignInClient) {

    private val mResources: Resources = mActivity.resources

    private var mCallback: SocialAuthCallback? = null
    private var mCallbackManager: CallbackManager? = null
    private var mSocialAuth: SocialAuth? = null

    enum class SocialAuth {
        ALL, GOOGLE, FACEBOOK, TWITTER
    }

    data class SocialAuthData(var token: String, var email: String, var name: String, var photo: String)

    interface SocialAuthCallback {
        fun onSignInSuccess(socialAuth: SocialAuth, data: SocialAuthData)

        fun onSignInFailed(socialAuth: SocialAuth, message: String)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (mSocialAuth) {
            SocialAuthManager.SocialAuth.GOOGLE -> onGoogleActivityResult(requestCode, resultCode, data)
            SocialAuthManager.SocialAuth.FACEBOOK -> onFacebookActivityResult(requestCode, resultCode, data)
            else -> {
                onFacebookActivityResult(requestCode, resultCode, data)
                onGoogleActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun init(socialAuth: SocialAuth, callback: SocialAuthCallback) {
        init(socialAuth)
        mCallback = callback
    }

    fun init(callback: SocialAuthCallback) {
        init(SocialAuth.ALL)
        mCallback = callback
    }

    fun destroy() {
        logout()
        when (mSocialAuth) {
            SocialAuthManager.SocialAuth.GOOGLE -> {
            }
            SocialAuthManager.SocialAuth.FACEBOOK -> destroyFacebook()
            SocialAuthManager.SocialAuth.TWITTER -> {
            }
            else -> {
            }
        }
    }

    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        mActivity.startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_LOGIN)
    }

    fun signInWithFacebook() {
        LoginManager.getInstance()
                .logInWithReadPermissions(mActivity, Arrays.asList("public_profile", "email"))
    }

    fun logout() {
        when (mSocialAuth) {
            SocialAuthManager.SocialAuth.FACEBOOK -> LoginManager.getInstance().logOut()
            SocialAuthManager.SocialAuth.GOOGLE -> mGoogleSignInClient.signOut()
            SocialAuthManager.SocialAuth.TWITTER -> {
            } // TODO: 05/04/2018
            SocialAuthManager.SocialAuth.ALL -> {
                LoginManager.getInstance().logOut()
                mGoogleSignInClient.signOut()
            }
        }
    }

    // private methods

    private fun init(socialAuth: SocialAuth) {
        mSocialAuth = socialAuth
        when (mSocialAuth) {
            SocialAuthManager.SocialAuth.GOOGLE -> {
            }
            SocialAuthManager.SocialAuth.FACEBOOK -> initFacebook()
            SocialAuthManager.SocialAuth.TWITTER -> {
            } // TODO: 05/04/2018
            else -> initFacebook()
        }
    }

    private fun onGoogleActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE_GOOGLE_LOGIN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess && result.signInAccount != null) {
                val account = result.signInAccount
                if (account != null) {
                    val socialAuthData = SocialAuthData(
                            account.idToken ?: "",
                            account.email ?: "",
                            account.displayName ?: "",
                            account.photoUrl?.toString() ?: ""
                    )
                    onGoogleSignInSuccess(socialAuthData)
                }
            } else {
                onGoogleSignInFailed(mResources.getString(R.string.google_signin_error))
            }
        }
    }

    private fun onGoogleSignInSuccess(data: SocialAuthData) {
            mCallback?.onSignInSuccess(SocialAuth.GOOGLE, data)
    }

    private fun onFacebookSignInSuccess(data: SocialAuthData) {
            mCallback?.onSignInSuccess(SocialAuth.FACEBOOK, data)
    }

    private fun onGoogleSignInFailed(reason: String) {
            mCallback?.onSignInFailed(SocialAuth.GOOGLE, reason)
        }

    private fun onFacebookSignInFailed(reason: String) {
            mCallback?.onSignInFailed(SocialAuth.FACEBOOK, reason)
    }

    private fun destroyFacebook() {
        if (mCallbackManager != null) {
            LoginManager.getInstance().unregisterCallback(mCallbackManager!!)
            mCallbackManager = null
        }
    }

    // Facebook Authorization
    private fun initFacebook() {
        if (mCallbackManager == null) {
            mCallbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().registerCallback(mCallbackManager!!, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Timber.d("SignWithFacebook: onSuccess: %s", loginResult.toString())

                    val accessToken = loginResult.accessToken
                    val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                        val socialAuthData = SocialAuthData(accessToken.token, "", "", "")
                        if (`object` != null) {
                            try {
                                if (`object`.has("email")) {
                                    socialAuthData.email = `object`.getString("email")
                                }
                                if (`object`.has("name")) {
                                    socialAuthData.name = `object`.getString("name")
                                }
                                if (`object`.has("picture")) {
                                    val jsonPicture = `object`.getJSONObject("picture")
                                    if (jsonPicture != null && jsonPicture.has("data")) {
                                        val jsonData = jsonPicture.getJSONObject("data")
                                        if (jsonData != null && jsonData.has("url")) {
                                            socialAuthData.photo = jsonData.getString("url")
                                        }
                                    }
                                }
                            } catch (e: JSONException) {
                                Timber.e(e, e.message)
                            }

                        }
                        onFacebookSignInSuccess(socialAuthData)
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id, email, name, picture.type(large)")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Timber.d("SignWithFacebook: onCancel")
                }

                override fun onError(exception: FacebookException) {
                    Timber.d("SignWithFacebook: onError: %s", exception.message)
                    if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                            signInWithFacebook()
                        }
                    } else {
                        onFacebookSignInFailed(mResources.getString(R.string.facebook_signin_error))
                    }
                }
            })
        }
    }

    private fun onFacebookActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        const val REQUEST_CODE_GOOGLE_LOGIN = 1023
    }

}
