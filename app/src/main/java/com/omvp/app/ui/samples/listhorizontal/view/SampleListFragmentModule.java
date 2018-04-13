package com.omvp.app.ui.samples.listhorizontal.view;

import android.app.Fragment;

import com.omvp.app.base.BaseFragmentModule;
import com.omvp.app.injector.scope.PerFragment;
import com.omvp.app.ui.samples.listhorizontal.presenter.SampleListHorizontalPresenterModule;

import dagger.Binds;
import dagger.Module;

/**
 * Provides SampleListHorizontalFragment fragment dependencies.
 */
@Module(includes = {
        BaseFragmentModule.class,
        SampleListHorizontalPresenterModule.class
})
public abstract class SampleListFragmentModule {

    /**
     * As per the contract specified in {@link BaseFragmentModule}; "This must be included in all
     * fragment modules, which must provide a concrete implementation of {@link Fragment}.
     *
     * @param fragment the SampleListHorizontalFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    abstract Fragment fragment(SampleListHorizontalFragment fragment);

    @Binds
    @PerFragment
    abstract SampleListHorizontalView view(SampleListHorizontalFragment fragment);

}
