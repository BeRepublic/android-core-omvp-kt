package com.omvp.app.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.omvp.app.util.DisposableManager

import javax.inject.Inject
import javax.inject.Named

import dagger.android.AndroidInjection
import timber.log.Timber

import com.omvp.app.base.BaseServiceModule.DISPOSABLE_SERVICE_MANAGER

/**
 * A service that extends FirebaseInstanceIdService to handle the creation, rotation, and updating
 * of registration tokens.
 */
class AppFirebaseInstanceIDService : FirebaseInstanceIdService() {

    @Inject
    @Named(DISPOSABLE_SERVICE_MANAGER)
    internal lateinit var mDisposableManager: DisposableManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        mDisposableManager.dispose()
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieveUser the token.
     */
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Timber.d("Refreshed token: %s", refreshedToken)

        // If you want to send messages to this application instance ormanage this apps
        // subscriptions on the server side, send the Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }

    /**
     * Persist token to third-party servers.
     *
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The add token.
     */
    private fun sendRegistrationToServer(token: String?) {

    }

}
