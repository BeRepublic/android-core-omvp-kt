package com.omvp.app.base.mvp

import android.app.Activity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.dialog.notice.view.NoticeDialogFragment
import com.omvp.app.dialog.notice.view.NoticeDialogFragmentModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import dagger.Binds
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

    /**
     * The main activity listens to the events in the [NoticeDialogFragment].
     *
     * @param activity the activity
     * @return the main fragment mCallback
     */
    @Binds
    @PerActivity
    internal abstract fun noticeDialogFragmentCallback(activity: BaseFragmentActivity): NoticeDialogFragment.FragmentCallback

    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivity
        internal fun baseFragmentActivity(activity: Activity): BaseFragmentActivity = activity as BaseFragmentActivity
    }
}
