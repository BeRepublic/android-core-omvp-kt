package com.omvp.app.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.omvp.app.util.DisposableManager;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.omvp.app.base.BaseServiceModule.DISPOSABLE_SERVICE_MANAGER;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    @Inject
    @Named(DISPOSABLE_SERVICE_MANAGER)
    protected DisposableManager mDisposableManager;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mDisposableManager.dispose();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d("onMessageReceived from: %s", remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Timber.d("Message data payload: %s", remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Timber.d("Message Notification Body: %s", remoteMessage.getNotification().getBody());
        }
    }
}
