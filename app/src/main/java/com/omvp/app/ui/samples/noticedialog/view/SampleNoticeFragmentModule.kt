package com.omvp.app.ui.samples.noticedialog.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.noticedialog.presenter.SampleNoticePresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SampleNoticeFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, SampleNoticePresenterModule::class))
abstract class SampleNoticeFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the SampleNoticeFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: SampleNoticeFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: SampleNoticeFragment): SampleNoticeView

}
