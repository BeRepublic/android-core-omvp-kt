package com.omvp.app.base

import com.omvp.app.injector.scope.PerBroadcastReceiver
import com.omvp.app.util.DisposableManager

import javax.inject.Named

import dagger.Module
import dagger.Provides

/**
 * Provides base broadcastreceiver dependencies. This must be included in all broadcastreceivers modules, which must
 * provide a concrete implementation of [android.content.BroadcastReceiver].
 */
@Module
object BaseBroadcastReceiverModule {

    internal const val DISPOSABLE_BROADCAST_RECEIVER_MANAGER = "BaseServiceModule.disposableBroascastReceiverManager"

    @JvmStatic
    @Provides
    @Named(DISPOSABLE_BROADCAST_RECEIVER_MANAGER)
    @PerBroadcastReceiver
    internal fun disposableBroadcastReceiverManager(): DisposableManager {
        return DisposableManager()
    }

}
