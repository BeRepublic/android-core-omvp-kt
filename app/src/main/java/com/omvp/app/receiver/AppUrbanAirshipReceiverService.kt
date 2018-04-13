package com.omvp.app.receiver

import android.content.Context
import android.content.Intent

import com.omvp.app.util.DisposableManager
import com.urbanairship.AirshipReceiver
import com.urbanairship.push.PushMessage

import javax.inject.Inject
import javax.inject.Named

import dagger.android.AndroidInjection
import timber.log.Timber

import com.omvp.app.base.BaseBroadcastReceiverModule.DISPOSABLE_BROADCAST_RECEIVER_MANAGER

class AppUrbanAirshipReceiverService : AirshipReceiver() {

    @Inject
    @Named(DISPOSABLE_BROADCAST_RECEIVER_MANAGER)
    lateinit var mDisposableManager: DisposableManager

    override fun onReceive(context: Context, intent: Intent?) {
        AndroidInjection.inject(this, context)
        super.onReceive(context, intent)
    }

    override fun onChannelCreated(context: Context, channelId: String) {
        Timber.i("Channel created. Channel Id:$channelId.")
    }

    override fun onChannelUpdated(context: Context, channelId: String) {
        Timber.i("Channel updated. Channel Id:$channelId.")
    }

    override fun onChannelRegistrationFailed(context: Context) {
        Timber.i("Channel registration failed.")
    }

    override fun onPushReceived(context: Context, message: PushMessage, notificationPosted: Boolean) {
        Timber.i("Received push message. Alert: " + message.alert + ". posted notification: " + notificationPosted)
    }

    override fun onNotificationPosted(context: Context, notificationInfo: AirshipReceiver.NotificationInfo) {
        Timber.i("Notification posted. Alert: " + notificationInfo.message.alert + ". NotificationId: " + notificationInfo.notificationId)
    }

    override fun onNotificationOpened(context: Context, notificationInfo: AirshipReceiver.NotificationInfo): Boolean {
        Timber.i("Notification opened. Alert: " + notificationInfo.message.alert + ". NotificationId: " + notificationInfo.notificationId)

        // Return false here to allow Urban Airship to auto launch the launcher activity
        return false
    }

    override fun onNotificationOpened(context: Context, notificationInfo: AirshipReceiver.NotificationInfo, actionButtonInfo: AirshipReceiver.ActionButtonInfo): Boolean {
        Timber.i("Notification action button opened. Button ID: " + actionButtonInfo.buttonId + ". NotificationId: " + notificationInfo.notificationId)

        // Return false here to allow Urban Airship to auto launch the launcher
        // activity for foreground notification action buttons
        return false
    }

    override fun onNotificationDismissed(context: Context, notificationInfo: AirshipReceiver.NotificationInfo) {
        Timber.i("Notification dismissed. Alert: " + notificationInfo.message.alert + ". Notification ID: " + notificationInfo.notificationId)
    }

}
