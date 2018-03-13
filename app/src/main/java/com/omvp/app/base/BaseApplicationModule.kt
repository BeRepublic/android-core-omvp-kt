package com.omvp.app.base

import android.app.Application
import android.content.ContentResolver
import android.content.Context

import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import com.omvp.app.injector.module.AnalyticsModule
import com.omvp.app.injector.module.CacheModule
import com.omvp.app.injector.module.GoogleModule
import com.omvp.app.injector.module.GsonModule
import com.omvp.app.injector.module.LocaleModule
import com.omvp.app.injector.module.LocationModule
import com.omvp.app.injector.module.MapperModule
import com.omvp.app.injector.module.NetworkModule
import com.omvp.app.injector.module.RepositoryModule
import com.omvp.app.util.TrackerManager
import com.raxdenstudios.commons.util.Utils
import com.raxdenstudios.preferences.AdvancedPreferences

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Provides application-wide dependencies.
 */
@Module(includes = arrayOf(
        LocaleModule::class,
        GsonModule::class,
        MapperModule::class,
        AnalyticsModule::class,
        CacheModule::class,
        RepositoryModule::class,
        LocationModule::class,
        GoogleModule::class,
        NetworkModule::class
))
abstract class BaseApplicationModule {

    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    @Binds
    @Singleton
    internal abstract fun application(application: BaseApplication): Application

    @Binds
    @Singleton
    internal abstract fun applicationContext(application: Application): Context

    @Module
    companion object {

        // =============================================================================================

        @JvmStatic
        @Provides
        @Singleton
        internal fun contentResolver(application: Application): ContentResolver {
            return application.contentResolver
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun advancedPreferences(application: Application): AdvancedPreferences {
            return AdvancedPreferences(application, Utils.getPackageName(application), Context.MODE_PRIVATE)
        }

        @JvmStatic
        @Provides
        @Singleton
        internal fun trackerManager(tracker: Tracker, firebaseAnalytics: FirebaseAnalytics): TrackerManager {
            return TrackerManager(tracker, firebaseAnalytics)
        }
    }

}
