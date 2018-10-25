package com.omvp.app.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omvp.app.helper.DialogHelper
import com.omvp.app.injector.module.InterceptorFragmentModule
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.util.DisposableManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of [Fragment].
 */
@Module(includes = arrayOf(InterceptorFragmentModule::class))
object BaseFragmentModule {

    internal const val CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager"
    internal const val DISPOSABLE_FRAGMENT_MANAGER = "BaseFragmentModule.disposableFragmentManager"

    @JvmStatic
    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    internal fun childFragmentManager(fragment: Fragment): FragmentManager {
        return fragment.childFragmentManager
    }

    @JvmStatic
    @Provides
    @Named(DISPOSABLE_FRAGMENT_MANAGER)
    @PerFragment
    internal fun disposableFragmentManager(): DisposableManager {
        return DisposableManager()
    }

    @JvmStatic
    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    internal fun dialogHelper(fragment: Fragment, @Named(CHILD_FRAGMENT_MANAGER) fragmentManager: FragmentManager): DialogHelper {
        return DialogHelper(fragment, fragmentManager)
    }
}
