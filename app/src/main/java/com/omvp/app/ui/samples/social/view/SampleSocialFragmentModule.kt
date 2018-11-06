package com.omvp.app.ui.samples.social.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.social.presenter.SampleSocialPresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named


@Module(includes = arrayOf(BaseFragmentModule::class, SampleSocialPresenterModule::class))
abstract class SampleSocialFragmentModule {
    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleMapFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleSocialFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleSocialFragment): SampleSocialView
}
