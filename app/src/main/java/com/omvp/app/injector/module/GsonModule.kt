package com.omvp.app.injector.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Provides application-wide dependencies.
 */
@Module
object GsonModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun gsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun gson(builder: GsonBuilder): Gson {
        return builder.create()
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("excludeFieldsWithoutExposeAnnotation")
    internal fun excludeFieldsWithoutExposeAnnotationGson(builder: GsonBuilder): Gson {
        builder.excludeFieldsWithoutExposeAnnotation()
        return builder.create()
    }

}
