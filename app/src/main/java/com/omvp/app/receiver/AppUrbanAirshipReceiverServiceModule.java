package com.omvp.app.receiver;

import android.content.BroadcastReceiver;

import com.omvp.app.base.BaseBroadcastReceiverModule;
import com.omvp.app.injector.scope.PerBroadcastReceiver;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        BaseBroadcastReceiverModule.class
})
public abstract class AppUrbanAirshipReceiverServiceModule {

    /**
     * As per the contract specified in {@link BaseBroadcastReceiverModule}; "This must be included
     * in all broadcastreceivers modules, which must provide a concrete implementation of
     * {@link BroadcastReceiver}."
     * <p>
     * This provides the service required to inject the dependencies into the
     * {@link com.omvp.app.base.BaseBroadcastReceiver}.
     *
     * @param receiver the AppUrbanAirshipReceiverService
     * @return the broadcastReceiver
     */
    @Binds
    @PerBroadcastReceiver
    abstract BroadcastReceiver broadcastReceiver(AppUrbanAirshipReceiverService receiver);

}
