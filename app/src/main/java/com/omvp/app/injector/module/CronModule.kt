package com.omvp.app.injector.module

import android.content.Context
import android.content.SharedPreferences

import com.raxdenstudios.cron.CronHandler
import com.raxdenstudios.cron.data.CronService
import com.raxdenstudios.cron.data.factory.CronPreferencesServiceImpl

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Provides application-wide dependencies.
 */
@Module
object CronModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun cronService(settings: SharedPreferences): CronService {
        return CronPreferencesServiceImpl(settings)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun cronHandler(context: Context, cronService: CronService): CronHandler {
        return CronHandler(context, cronService)
    }

}
