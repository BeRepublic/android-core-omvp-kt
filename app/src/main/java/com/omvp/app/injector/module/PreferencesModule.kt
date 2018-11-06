package com.omvp.app.injector.module

import android.app.Application
import android.content.Context
import com.raxdenstudios.commons.util.Utils
import com.raxdenstudios.preferences.AdvancedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides application-wide dependencies.
 */
@Module
object PreferencesModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun advancedPreferences(application: Application): AdvancedPreferences {
        return AdvancedPreferences(application, Utils.getPackageName(application), Context.MODE_PRIVATE)
    }

}