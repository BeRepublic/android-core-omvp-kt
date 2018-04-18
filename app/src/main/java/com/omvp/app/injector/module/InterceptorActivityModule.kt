package com.omvp.app.injector.module

import android.app.Activity
import android.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.LocationRequest
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.interceptor.ToolbarActivityInterceptor
import com.omvp.app.interceptor.authPhone.AuthPhoneActivityInterceptor
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptor
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptorCallback
import com.omvp.app.interceptor.google.GoogleApiClientActivityInterceptor
import com.omvp.app.interceptor.google.GoogleApiClientInterceptor
import com.omvp.app.interceptor.google.GoogleApiClientInterceptorCallback
import com.omvp.app.interceptor.location.LocationActivityInterceptor
import com.omvp.app.interceptor.location.LocationInterceptor
import com.omvp.app.interceptor.operation.OperationBroadcastActivityInterceptor
import com.omvp.app.interceptor.operation.OperationBroadcastInterceptor
import com.omvp.app.interceptor.takePicture.TakePictureActivityInterceptor
import com.omvp.app.interceptor.takePicture.TakePictureInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.injectfragmentlist.InjectFragmentListActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragmentlist.InjectFragmentListInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragmentlist.InjectFragmentListInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.network.NetworkActivityInterceptor
import com.raxdenstudios.square.interceptor.commons.network.NetworkInterceptor
import com.raxdenstudios.square.interceptor.commons.network.NetworkInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback
import dagger.Module
import dagger.Provides

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
object InterceptorActivityModule {

    @JvmStatic
    @Provides
    @PerActivity
    fun autoInflateLayoutInterceptor(activity: Activity): AutoInflateLayoutInterceptor {
        return AutoInflateLayoutActivityInterceptor(activity, activity as AutoInflateLayoutInterceptorCallback)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun injectFragmentInterceptor(activity: Activity): InjectFragmentInterceptor {
        return InjectFragmentActivityInterceptor(activity, activity as InjectFragmentInterceptorCallback<*>)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun networkInterceptor(activity: Activity): NetworkInterceptor {
        return NetworkActivityInterceptor(activity, activity as NetworkInterceptorCallback)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun toolbarInterceptor(activity: Activity): ToolbarInterceptor {
        return ToolbarActivityInterceptor(activity, activity as ToolbarInterceptorCallback)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun fragmentStatePagerInterceptor(activity: Activity): FragmentStatePagerInterceptor<*> {
        return FragmentStatePagerActivityInterceptor<Fragment>(activity, activity as FragmentStatePagerInterceptorCallback<*>)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun injectFragmentListInterceptor(activity: Activity): InjectFragmentListInterceptor {
        return InjectFragmentListActivityInterceptor(activity, activity as InjectFragmentListInterceptorCallback<*>)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun provideLocationInterceptor(activity: Activity, locationRequest: LocationRequest): LocationInterceptor {
        return LocationActivityInterceptor(activity, locationRequest)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun provideGoogleApiClientInterceptor(activity: Activity, googleSignInOptions: GoogleSignInOptions): GoogleApiClientInterceptor {
        return GoogleApiClientActivityInterceptor(activity, googleSignInOptions, activity as GoogleApiClientInterceptorCallback)
    }

    @JvmStatic
    @Provides
    @PerActivity
    fun provideGalleryInterceptor(activity: Activity): TakePictureInterceptor {
        return TakePictureActivityInterceptor(activity)
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun operationBroadcastInterceptor(activity: Activity): OperationBroadcastInterceptor {
        return OperationBroadcastActivityInterceptor(activity)
    }

    @JvmStatic
    @Provides
    @PerActivity
    internal fun provideAuthPhoneInterceptor(activity: Activity): AuthPhoneInterceptor? {
        return if (activity is AuthPhoneInterceptorCallback) {
            AuthPhoneActivityInterceptor(activity, activity as AuthPhoneInterceptorCallback)
        } else {
            null
        }
    }

}
