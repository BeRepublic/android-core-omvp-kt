package com.omvp.app.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.omvp.app.util.DisposableManager

import javax.inject.Inject
import javax.inject.Named

import dagger.android.AndroidInjection

import com.omvp.app.base.BaseBroadcastReceiverModule.DISPOSABLE_BROADCAST_RECEIVER_MANAGER

abstract class BaseBroadcastReceiver : BroadcastReceiver() {

    @Inject
    @Named(DISPOSABLE_BROADCAST_RECEIVER_MANAGER)
    protected lateinit var mDisposableManager: DisposableManager

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
    }

}
