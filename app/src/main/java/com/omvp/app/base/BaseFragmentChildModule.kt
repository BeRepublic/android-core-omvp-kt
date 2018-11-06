package com.omvp.app.base

import androidx.fragment.app.Fragment

import dagger.Module

/**
 * Provides base fragment dependencies. This must be included in all fragment modules, which must
 * provide a concrete implementation of [Fragment].
 */
@Module
abstract class BaseFragmentChildModule {

    companion object {
        const val FRAGMENT_CHILD = "BaseFragmentChildModule.fragmentChild"
    }

}
