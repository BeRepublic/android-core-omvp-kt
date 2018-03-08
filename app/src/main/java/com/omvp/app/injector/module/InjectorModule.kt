package com.omvp.app.injector.module

import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.ui.home.HomeActivity
import com.omvp.app.ui.home.HomeActivityModule
import com.omvp.app.ui.samples.sample.SampleActivity
import com.omvp.app.ui.ktsamples.ktsample.SampleActivityModule
import com.omvp.app.ui.samples.samplemultiple.SampleMultipleActivity
import com.omvp.app.ui.samples.samplemultiple.SampleMultipleActivityModule
import com.omvp.app.ui.samples.samplelist.SampleListActivity
import com.omvp.app.ui.samples.samplelist.SampleListActivityModule
import com.omvp.app.ui.samples.sample_location.SampleLocationActivity
import com.omvp.app.ui.samples.sample_location.SampleLocationActivityModule
import com.omvp.app.ui.samples.sample_take_picture.SampleTakePictureActivity
import com.omvp.app.ui.samples.sample_take_picture.SampleTakePictureActivityModule
import com.omvp.app.ui.samples.samplepager.SamplePagerActivity
import com.omvp.app.ui.samples.samplepager.SamplePagerActivityModule
import com.omvp.app.ui.splash.SplashActivity
import com.omvp.app.ui.splash.SplashActivityModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides application-wide dependencies.
 */
@Module
abstract class InjectorModule {

    /**
     * Provides the injector for the [SplashActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SplashActivityModule::class))
    abstract fun splashActivity(): SplashActivity

    /**
     * Provides the injector for the [HomeActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(HomeActivityModule::class))
    internal abstract fun homeActivity(): HomeActivity

    /**
     * Provides the injector for the [SampleActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleActivityModule::class))
    internal abstract fun sampleActivity(): SampleActivity

    /**
     * Provides the injector for the [SampleListActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleListActivityModule::class))
    internal abstract fun sampleListActivity(): SampleListActivity

    /**
     * Provides the injector for the [SamplePagerActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SamplePagerActivityModule::class))
    internal abstract fun samplePagerActivity(): SamplePagerActivity

    /**
     * Provides the injector for the [SampleMultipleActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleMultipleActivityModule::class))
    internal abstract fun sampleMapActivity(): SampleMultipleActivity

    /**
     * Provides the injector for the [SampleLocationActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleLocationActivityModule::class))
    internal abstract fun sampleLocationActivity(): SampleLocationActivity

    /**
     * Provides the injector for the [SampleLocationActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleTakePictureActivityModule::class))
    internal abstract fun sampleTakePictureActivity(): SampleTakePictureActivity
}
