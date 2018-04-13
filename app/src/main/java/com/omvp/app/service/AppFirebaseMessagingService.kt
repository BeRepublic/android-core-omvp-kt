package com.omvp.app.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.omvp.app.util.DisposableManager

import javax.inject.Inject
import javax.inject.Named

import dagger.android.AndroidInjection
import timber.log.Timber

import com.omvp.app.base.BaseServiceModule.DISPOSABLE_SERVICE_MANAGER

class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    @Named(DISPOSABLE_SERVICE_MANAGER)
    lateinit var mDisposableManager: DisposableManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        mDisposableManager.dispose()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("onMessageReceived from: %s", remoteMessage!!.from)
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: %s", remoteMessage.data)
        }
        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Timber.d("Message Notification Body: %s", remoteMessage.notification!!.body)
        }
    }
}
