package com.omvp.app.base

import com.omvp.app.injector.scope.PerBroadcastReceiver
import com.omvp.app.util.DisposableManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Provides base broadcastreceiver dependencies. This must be included in all broadcastreceivers modules, which must
 * provide a concrete implementation of [android.content.BroadcastReceiver].
 */
@Module
abstract class BaseBroadcastReceiverModule {

    @Module
    companion object {
        const val DISPOSABLE_BROADCAST_RECEIVER_MANAGER = "BaseServiceModule.disposableBroascastReceiverManager"

        @JvmStatic
        @Provides
        @Named(DISPOSABLE_BROADCAST_RECEIVER_MANAGER)
        @PerBroadcastReceiver
        internal fun disposableBroadcastReceiverManager() = DisposableManager()
    }
}
