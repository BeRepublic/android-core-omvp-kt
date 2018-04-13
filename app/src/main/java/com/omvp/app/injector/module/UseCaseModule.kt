package com.omvp.app.injector.module

import com.omvp.app.injector.scope.PerActivity
import com.omvp.domain.interactor.*
import com.omvp.domain.interactor.impl.*

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {

    @Binds
    @Singleton
    internal abstract fun saveLocaleUseCase(usecase: SaveLocaleUseCaseImpl): SaveLocaleUseCase

    @Binds
    @Singleton
    internal abstract fun getLocaleUseCase(usecase: GetLocaleUseCaseImpl): GetLocaleUseCase

    @Binds
    @Singleton
    internal abstract fun getLocaleListUseCase(usecase: GetLocaleListUseCaseImpl): GetLocaleListUseCase

    @Binds
    @Singleton
    internal abstract fun registerDeviceUseCase(usecase: RegisterDeviceUseCaseImpl): RegisterDeviceUseCase

    // =============== SAMPLE ======================================================================

    @Binds
    @Singleton
    internal abstract fun getSampleUseCase(usecase: GetSampleUseCaseImpl): GetSampleUseCase

    @Binds
    @Singleton
    internal abstract fun getSampleListUseCase(usecase: GetSampleListUseCaseImpl): GetSampleListUseCase

    @Binds
    @Singleton
    internal abstract fun removeSampleUseCase(usecase: RemoveSampleUseCaseImpl): RemoveSampleUseCase

    @Binds
    @Singleton
    internal abstract fun saveSampleUseCase(usecase: SaveSampleUseCaseImpl): SaveSampleUseCase

}
