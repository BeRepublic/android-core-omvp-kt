package com.omvp.app.injector.module

import android.app.Activity
import android.app.FragmentManager

import com.omvp.app.helper.AnimationHelper
import com.omvp.app.helper.DialogHelper
import com.omvp.app.helper.NavigationHelper
import com.omvp.app.helper.SnackBarHelper
import com.omvp.app.injector.scope.PerActivity

import dagger.Module
import dagger.Provides

/**
 * Created by rakshakhegde on 26/04/17.
 */
@Module
object HelperModule {

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

}
