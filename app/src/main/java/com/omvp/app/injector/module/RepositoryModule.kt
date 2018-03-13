package com.omvp.app.injector.module

import com.omvp.data.repository.CredentialsRepositoryImpl
import com.omvp.data.repository.LocaleRepositoryImpl
import com.omvp.data.repository.SampleRepositoryImpl
import com.omvp.domain.repository.CredentialsRepository
import com.omvp.domain.repository.LocaleRepository
import com.omvp.domain.repository.SampleRepository

import javax.inject.Singleton

import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun sampleRepository(repository: SampleRepositoryImpl): SampleRepository

    @Binds
    @Singleton
    internal abstract fun localeRepository(repository: LocaleRepositoryImpl): LocaleRepository

    @Binds
    @Singleton
    internal abstract fun credentialsRepository(repository: CredentialsRepositoryImpl): CredentialsRepository

}
