package com.omvp.app.injector.module

import java.util.Locale

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


@Module
object LocaleModule {

    @JvmStatic
    @Provides
    @IntoSet
    @Singleton
    internal fun localeUK(): Locale {
        return Locale.UK
    }

    @JvmStatic
    @Provides
    @IntoSet
    @Singleton
    internal fun localeES(): Locale {
        return Locale("es", "ES")
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("default")
    internal fun localeDefault(): Locale {
        return Locale("es", "ES")
    }


}
