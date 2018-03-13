package com.omvp.app.injector.module

import android.content.Context

import java.io.File

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache

@Module
object CacheModule {

    private const val CACHE_DIRECTORY = "responses"
    private const val CACHE_SIZE = 10 * 1024 * 1024         // 10 MiB;

    @JvmStatic
    @Provides
    @Singleton
    internal fun cache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, CACHE_DIRECTORY)
        return Cache(httpCacheDirectory, CACHE_SIZE.toLong())
    }

}
