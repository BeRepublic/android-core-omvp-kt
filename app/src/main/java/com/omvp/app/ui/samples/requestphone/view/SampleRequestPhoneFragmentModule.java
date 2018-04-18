package com.omvp.app.ui.samples.requestphone.view;

import android.app.Fragment;

import com.omvp.app.base.BaseFragmentModule;
import com.omvp.app.injector.scope.PerFragment;
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterModule;

import dagger.Binds;
import dagger.Module;

/**
 * Provides SampleRequestPhoneFlowFragment fragment dependencies.
 */
@Module(includes = {
        BaseFragmentModule.class,
        SampleRequestPhonePresenterModule.class
})
public abstract class SampleRequestPhoneFragmentModule {

    /**
     * As per the contract specified in {@link BaseFragmentModule}; "This must be included in all
     * fragment modules, which must provide a concrete implementation of {@link Fragment}.
     *
     * @param fragment the SampleRequestPhoneFlowFragment
     * @return the fragment
     */
    @Binds
    @PerFragment
    abstract Fragment fragment(SampleRequestPhoneFragment fragment);

    @Binds
    @PerFragment
    abstract SampleRequestPhoneView view(SampleRequestPhoneFragment fragment);

}
