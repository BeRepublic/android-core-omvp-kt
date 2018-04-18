package com.omvp.app.ui.samples.social.presenter;

import com.omvp.app.base.mvp.presenter.BasePresenterModule;
import com.omvp.app.injector.scope.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Provides SampleTopPresenterModule dependencies.
 */
@Module(includes = BasePresenterModule.class)
public abstract class SampleSocialPresenterModule {

    @Binds
    @PerFragment
    abstract SampleSocialPresenter presenter(SampleSocialPresenterImpl presenter);

}