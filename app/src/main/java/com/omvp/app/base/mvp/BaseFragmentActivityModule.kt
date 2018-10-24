package com.omvp.app.base.mvp

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.dialog.notice.view.NoticeDialogFragment
import com.omvp.app.dialog.notice.view.NoticeDialogFragmentModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module(includes = arrayOf(BaseActivityModule::class))
abstract class BaseFragmentActivityModule {

    /**
     * Provides the injector for the [NoticeDialogFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(NoticeDialogFragmentModule::class))
    internal abstract fun fragmentInjector(): NoticeDialogFragment

    @Module
    companion object {

        /**
         * The main activity listens to the events in the [NoticeDialogFragment].
         *
         * @param activity the activity
         * @return the main fragment callback
         */
        @JvmStatic
        @Provides
        @PerActivity
        internal fun fragmentCallback(activity: BaseFragmentActivity): NoticeDialogFragment.FragmentCallback {
            return activity
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun baseFragmentActivity(activity: Activity): BaseFragmentActivity {
            return activity as BaseFragmentActivity
        }

        @JvmStatic
        @Provides
        @PerActivity
        internal fun activityFragmentManager(activity: Activity): FragmentManager {
            return (activity as FragmentActivity).supportFragmentManager
        }
    }
}
