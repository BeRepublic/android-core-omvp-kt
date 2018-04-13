package com.omvp.app.injector.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.raxdenstudios.commons.util.Utils
import com.raxdenstudios.preferences.AdvancedPreferences

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Provides application-wide dependencies.
 */
@Module
object PreferencesModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun sharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Utils.getPackageName(application), Context.MODE_PRIVATE)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun advancedPreferences(sharedPreferences: SharedPreferences): AdvancedPreferences {
        return AdvancedPreferences(sharedPreferences)
    }

}