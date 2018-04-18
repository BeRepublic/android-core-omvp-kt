package com.omvp.app.ui.samples.bottomnavigation

import android.app.Activity
import com.omvp.app.base.BaseActivity
import com.omvp.app.base.BaseActivityModule
import com.omvp.app.base.mvp.BaseFragmentActivityModule
import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerFragment
import com.omvp.app.ui.samples.bottomnavigation.view.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides splash activity dependencies
 */
@Module(includes = arrayOf(BaseFragmentActivityModule::class))
abstract class BottomNavigationActivityModule {

    /**
     * As per the contract specified in [BaseActivityModule]; "This must be included in all
     * activity modules, which must provide a concrete implementation of [Activity]."
     *
     *
     * This provides the activity required to inject the dependencies into the
     * [BaseActivity].
     *
     * @param activity the SplashActivity
     * @return the activity
     */
    @Binds
    @PerActivity
    internal abstract fun activity(activity: BottomNavigationActivity): Activity

    // =============================================================================================

    /**
     * The main activity listens to the events in the [BottomNavigationFirstFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun firstFragmentCallback(activity: BottomNavigationActivity): BottomNavigationFirstFragment.BottomNavigationFirstFragmentCallback

    /**
     * The main activity listens to the events in the [BottomNavigationSecondFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun secondFragmentCallback(activity: BottomNavigationActivity): BottomNavigationSecondFragment.BottomNavigationSecondFragmentCallback

    /**
     * The main activity listens to the events in the [BottomNavigationThirdFragment].
     *
     * @param activity the activity
     * @return the main fragment callback
     */
    @Binds
    @PerActivity
    internal abstract fun thirdFragmentCallback(activity: BottomNavigationActivity): BottomNavigationThirdFragment.BottomNavigationThirdFragmentCallback

    /**
     * Provides the injector for the [BottomNavigationFirstFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(BottomNavigationFirstFragmentModule::class))
    internal abstract fun firstFragmentInjector(): BottomNavigationFirstFragment

    /**
     * Provides the injector for the [BottomNavigationSecondFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(BottomNavigationSecondFragmentModule::class))
    internal abstract fun secondFragmentInjector(): BottomNavigationSecondFragment

    /**
     * Provides the injector for the [BottomNavigationThirdFragment], which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(BottomNavigationThirdFragmentModule::class))
    internal abstract fun thirdFragmentInjector(): BottomNavigationThirdFragment

}
