package com.omvp.app.base

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import com.google.android.gms.analytics.Tracker
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.analytics.FirebaseAnalytics
import com.omvp.app.BuildConfig
import com.omvp.app.helper.AnimationHelper
import com.omvp.app.helper.DialogHelper
import com.omvp.app.helper.NavigationHelper
import com.omvp.app.helper.SnackBarHelper

import com.omvp.app.injector.module.InterceptorActivityModule
import com.omvp.app.injector.module.UseCaseModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.util.DisposableManager
import com.omvp.app.util.OperationBroadcastManager
import com.omvp.app.util.SocialAuthManager
import com.omvp.app.util.TrackerManager
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Binds

import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module(includes = arrayOf(
        InterceptorActivityModule::class
))
abstract class BaseActivityModule {

    @Binds
    @Named(ACTIVITY_CONTEXT)
    @PerActivity
    internal abstract fun activityContext(activity: Activity): Context

    @Module
    companion object {

        const val ACTIVITY_CONTEXT = "BaseActivityModule.activityContext"

        @JvmStatic
        @Provides
        @PerActivity
        internal fun resources(activity: Activity): Resources = activity.resources

        @JvmStatic
        @Provides
        @PerActivity
        internal fun activityExtras(activity: Activity): Bundle {
            return if (activity.intent != null && activity.intent.extras != null) activity.intent.extras else Bundle()
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun rxPermission(activity: Activity): RxPermissions =
                RxPermissions(activity).apply {
                    setLogging(BuildConfig.DEBUG)
                }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun activityDisposableManager(): DisposableManager = DisposableManager()

        @JvmStatic
        @Provides
        @PerActivity
        internal fun trackerManager(activity: Activity, tracker: Tracker, firebaseAnalytics: FirebaseAnalytics): TrackerManager {
            return TrackerManager(tracker, firebaseAnalytics)
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun navigationHelper(activity: Activity): NavigationHelper {
            return NavigationHelper(activity)
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun dialogHelper(activity: Activity, fragmentManager: FragmentManager): DialogHelper {
            return DialogHelper(activity, fragmentManager)
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun snackBarHelper(activity: Activity): SnackBarHelper {
            return SnackBarHelper(activity)
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun animationHelper(activity: Activity): AnimationHelper {
            return AnimationHelper(activity)
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun operationBroadcastManager(activity: Activity): OperationBroadcastManager =
                OperationBroadcastManager(activity)


        @JvmStatic
        @Provides
        @PerActivity
        internal fun activitySocialAuthManager(activity: Activity, googleSignInClient: GoogleSignInClient): SocialAuthManager {
            return SocialAuthManager(activity, googleSignInClient)
        }
    }
}
