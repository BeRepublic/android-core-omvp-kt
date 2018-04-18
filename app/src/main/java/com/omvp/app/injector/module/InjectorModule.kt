package com.omvp.app.injector.module

import com.omvp.app.injector.scope.PerActivity
import com.omvp.app.injector.scope.PerBroadcastReceiver
import com.omvp.app.injector.scope.PerService
import com.omvp.app.receiver.AppUrbanAirshipReceiverService
import com.omvp.app.receiver.AppUrbanAirshipReceiverServiceModule
import com.omvp.app.service.AppFirebaseInstanceIDService
import com.omvp.app.service.AppFirebaseInstanceIDServiceModule
import com.omvp.app.service.AppFirebaseMessagingService
import com.omvp.app.service.AppFirebaseMessagingServiceModule
import com.omvp.app.ui.ktsamples.ktsample.SampleActivityModule
import com.omvp.app.ui.samples.detail.SampleDetailActivity
import com.omvp.app.ui.samples.detail.SampleDetailActivityModule
import com.omvp.app.ui.samples.home.HomeActivity
import com.omvp.app.ui.samples.home.HomeActivityModule
import com.omvp.app.ui.samples.inputs.SampleInputActivity
import com.omvp.app.ui.samples.inputs.SampleInputActivityModule
import com.omvp.app.ui.samples.list.SampleListActivity
import com.omvp.app.ui.samples.list.SampleListActivityModule
import com.omvp.app.ui.samples.listhorizontal.SampleListHorizontalActivity
import com.omvp.app.ui.samples.listhorizontal.SampleListHorizontalActivityModule
import com.omvp.app.ui.samples.locale.SampleLocaleActivity
import com.omvp.app.ui.samples.locale.SampleLocaleActivityModule
import com.omvp.app.ui.samples.location.SampleLocationActivity
import com.omvp.app.ui.samples.location.SampleLocationActivityModule
import com.omvp.app.ui.samples.multiple.SampleMultipleActivity
import com.omvp.app.ui.samples.multiple.SampleMultipleActivityModule
import com.omvp.app.ui.samples.pager.SamplePagerActivity
import com.omvp.app.ui.samples.pager.SamplePagerActivityModule
import com.omvp.app.ui.samples.simple.SampleActivity
import com.omvp.app.ui.samples.takepicture.SampleTakePictureActivity
import com.omvp.app.ui.samples.takepicture.SampleTakePictureActivityModule
import com.omvp.app.ui.samples.vibration.VibrationActivity
import com.omvp.app.ui.samples.vibration.VibrationActivityModule
import com.omvp.app.ui.splash.SplashActivity
import com.omvp.app.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides application-wide dependencies.
 */
@Module
abstract class InjectorModule {

    // ============= Services ======================================================================

    /**
     * Provides the injector for the [AppFirebaseInstanceIDService], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerService
    @ContributesAndroidInjector(modules = arrayOf(AppFirebaseInstanceIDServiceModule::class))
    internal abstract fun appInstanceIDService(): AppFirebaseInstanceIDService

    /**
     * Provides the injector for the [AppFirebaseInstanceIDService], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerService
    @ContributesAndroidInjector(modules = arrayOf(AppFirebaseMessagingServiceModule::class))
    internal abstract fun appFirebaseMessagingService(): AppFirebaseMessagingService

    // ============= BroadcastReceivers ============================================================

    /**
     * Provides the injector for the [AppFirebaseInstanceIDService], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerBroadcastReceiver
    @ContributesAndroidInjector(modules = arrayOf(AppUrbanAirshipReceiverServiceModule::class))
    internal abstract fun appUrbanAirshipReceiverService(): AppUrbanAirshipReceiverService

    // ============= Activities ====================================================================

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
     * Provides the injector for the [SampleListHorizontalActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleListHorizontalActivityModule::class))
    internal abstract fun sampleListHorizontalActivity(): SampleListHorizontalActivity

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
     * Provides the injector for the [SampleTakePictureActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleTakePictureActivityModule::class))
    internal abstract fun sampleTakePictureActivity(): SampleTakePictureActivity

    /**
     * Provides the injector for the [SampleDetailActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleDetailActivityModule::class))
    internal abstract fun sampleDetailActivity(): SampleDetailActivity

    /**
     * Provides the injector for the [SampleDetailActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleLocaleActivityModule::class))
    internal abstract fun sampleLocaleActivity(): SampleLocaleActivity

    /**
     * Provides the injector for the [VibrationActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(VibrationActivityModule::class))
    internal abstract fun vibrationActivity(): VibrationActivity

    /**
     * Provides the injector for the [SampleInputActivity], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SampleInputActivityModule::class))
    internal abstract fun sampleInputActivity(): SampleInputActivity
}
