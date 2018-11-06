package com.omvp.app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omvp.app.helper.DialogHelper
import com.omvp.app.injector.scope.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of [Fragment].
 */
@Module
abstract class BaseFragmentModule {

    @Module
    companion object {

        internal const val FRAGMENT = "BaseFragmentModule.fragment"
        internal const val CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager"
        internal const val FRAGMENT_DIALOG_HELPER = "BaseFragmentModule.dialogHelper"
        internal const val FRAGMENT_ARGUMENTS = "BaseFragmentModule.fragmentArguments"

        @JvmStatic
        @Provides
        @Named(FRAGMENT_ARGUMENTS)
        @PerFragment
        internal fun arguments(@Named(FRAGMENT) fragment: Fragment) = fragment.arguments ?: Bundle()

        @JvmStatic
        @Provides
        @Named(CHILD_FRAGMENT_MANAGER)
        @PerFragment
        internal fun childFragmentManager(@Named(FRAGMENT) fragment: Fragment) = fragment.childFragmentManager

        @JvmStatic
        @Provides
        @Named(FRAGMENT_DIALOG_HELPER)
        @PerFragment
        internal fun dialogHelper(@Named(FRAGMENT) fragment: Fragment, @Named(CHILD_FRAGMENT_MANAGER) fragmentManager: FragmentManager) = DialogHelper(fragment, fragmentManager)
    }
}
