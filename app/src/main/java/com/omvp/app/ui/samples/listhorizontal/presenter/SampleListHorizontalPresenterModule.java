package com.omvp.app.ui.samples.listhorizontal.presenter;

import com.omvp.app.base.mvp.presenter.BasePresenterModule;
import com.omvp.app.injector.scope.PerFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Provides SampleListHorizontalPresenterModule dependencies.
 */
@Module(includes = BasePresenterModule.class)
public abstract class SampleListHorizontalPresenterModule {

    @Binds
    @PerFragment
    abstract SampleListHorizontalPresenter presenter(SampleListHorizontalPresenterImpl presenter);

}