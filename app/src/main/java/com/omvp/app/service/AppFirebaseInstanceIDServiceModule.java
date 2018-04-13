package com.omvp.app.service;

import android.app.Service;

import com.omvp.app.base.BaseService;
import com.omvp.app.base.BaseServiceModule;
import com.omvp.app.injector.scope.PerService;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        BaseServiceModule.class
})
public abstract class AppFirebaseInstanceIDServiceModule {

    /**
     * As per the contract specified in {@link BaseServiceModule}; "This must be included in all
     * services modules, which must provide a concrete implementation of {@link Service}."
     * <p>
     * This provides the service required to inject the dependencies into the
     * {@link BaseService}.
     *
     * @param service the AppFirebaseInstanceIDService
     * @return the service
     */
    @Binds
    @PerService
    abstract Service service(AppFirebaseInstanceIDService service);

}
