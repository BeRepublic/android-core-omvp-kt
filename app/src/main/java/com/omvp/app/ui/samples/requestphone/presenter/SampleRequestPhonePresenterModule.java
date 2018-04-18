package com.omvp.app.ui.samples.requestphone.presenter;

import com.omvp.app.base.mvp.presenter.BasePresenterModule;
import com.omvp.app.injector.scope.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Provides SampleRequestPhoneFlowPresenterModule dependencies.
 */
@Module(includes = BasePresenterModule.class)
public abstract class SampleRequestPhonePresenterModule {

    @Binds
    @PerFragment
    abstract SampleRequestPhonePresenter presenter(SampleRequestPhonePresenterImpl presenter);

}
