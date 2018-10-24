package com.omvp.app.injector.module

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import com.omvp.app.injector.scope.PerFragment
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewDialogFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsDialogFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsInterceptor
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsInterceptorCallback

import dagger.Module
import dagger.Provides

/**
 * A module to wrap the Fragment state and expose it to the graph.
 */
@Module
object InterceptorFragmentModule {

    @JvmStatic
    @Provides
    @PerFragment
    internal fun autoInflateViewInterceptor(fragment: Fragment): AutoInflateViewInterceptor {
//        if (fragment !is AutoInflateViewInterceptorCallback) return null
        return if (fragment is DialogFragment) {
            AutoInflateViewDialogFragmentInterceptor(fragment, fragment as AutoInflateViewInterceptorCallback)
        } else {
            AutoInflateViewFragmentInterceptor(fragment, fragment as AutoInflateViewInterceptorCallback)
        }
    }

    @JvmStatic
    @Provides
    @PerFragment
    internal fun handleArgumentsInterceptor(fragment: Fragment): HandleArgumentsInterceptor {
//        if (fragment !is HandleArgumentsInterceptorCallback) return null
        return if (fragment is DialogFragment) {
            HandleArgumentsDialogFragmentInterceptor(fragment, fragment as HandleArgumentsInterceptorCallback)
        } else {
            HandleArgumentsFragmentInterceptor(fragment, fragment as HandleArgumentsInterceptorCallback)
        }
    }

}
