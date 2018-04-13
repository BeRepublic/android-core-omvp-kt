package com.omvp.app.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.omvp.app.util.DisposableManager;
import com.urbanairship.AirshipReceiver;
import com.urbanairship.push.PushMessage;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.omvp.app.base.BaseBroadcastReceiverModule.DISPOSABLE_BROADCAST_RECEIVER_MANAGER;

public class AppUrbanAirshipReceiverService extends AirshipReceiver {

    @Inject
    @Named(DISPOSABLE_BROADCAST_RECEIVER_MANAGER)
    protected DisposableManager mDisposableManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        AndroidInjection.inject(this, context);
        super.onReceive(context, intent);
    }

    @Override
    protected void onChannelCreated(@NonNull Context context, @NonNull String channelId) {
        Timber.i("Channel created. Channel Id:" + channelId + ".");
    }

    @Override
    protected void onChannelUpdated(@NonNull Context context, @NonNull String channelId) {
        Timber.i("Channel updated. Channel Id:" + channelId + ".");
    }

    @Override
    protected void onChannelRegistrationFailed(Context context) {
        Timber.i("Channel registration failed.");
    }

    @Override
    protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
        Timber.i("Received push message. Alert: " + message.getAlert() + ". posted notification: " + notificationPosted);
    }

    @Override
    protected void onNotificationPosted(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Timber.i("Notification posted. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());
    }

    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Timber.i("Notification opened. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());

        // Return false here to allow Urban Airship to auto launch the launcher activity
        return false;
    }

    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo, @NonNull ActionButtonInfo actionButtonInfo) {
        Timber.i("Notification action button opened. Button ID: " + actionButtonInfo.getButtonId() + ". NotificationId: " + notificationInfo.getNotificationId());

        // Return false here to allow Urban Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false;
    }

    @Override
    protected void onNotificationDismissed(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Timber.i("Notification dismissed. Alert: " + notificationInfo.getMessage().getAlert() + ". Notification ID: " + notificationInfo.getNotificationId());
    }
    
}
