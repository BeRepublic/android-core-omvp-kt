package com.omvp.app.dialog.notice.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.dialog.notice.presenter.NoticeDialogPresenterModule
import com.omvp.app.injector.scope.PerFragment
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides SampleMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, NoticeDialogPresenterModule::class))
abstract class NoticeDialogFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the HomeFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun dialogFragment(fragment: NoticeDialogFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun dialogView(fragment: NoticeDialogFragment): NoticeDialogView

}
