package com.omvp.app.ui.samples.coroutine.view

import androidx.fragment.app.Fragment
import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.coroutine.presenter.CoroutinePresenterModule
import dagger.Binds
import dagger.Module
import javax.inject.Named

/**
 * Provides CoroutineMapFragment fragment dependencies.
 */
@Module(includes = arrayOf(BaseFragmentModule::class, CoroutinePresenterModule::class))
abstract class CoroutineFragmentModule {

    /**
     * As per the contract specified in [BaseFragmentModule]; "This must be included in all
     * fragment modules, which must provide a concrete implementation of [Fragment].
     *
     * @param fragment the CoroutineMapFragment
     * @return the fragment
     */
    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @PerFragment
    internal abstract fun fragment(fragment: CoroutineFragment): Fragment

    @Binds
    @PerFragment
    internal abstract fun view(fragment: CoroutineFragment): CoroutineView

}
