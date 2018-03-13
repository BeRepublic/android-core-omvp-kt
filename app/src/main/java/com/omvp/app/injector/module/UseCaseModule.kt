package com.omvp.app.injector.module

import com.omvp.app.injector.scope.PerActivity
import com.omvp.domain.interactor.GetSampleListUseCase
import com.omvp.domain.interactor.GetSampleUseCase
import com.omvp.domain.interactor.RemoveSampleUseCase
import com.omvp.domain.interactor.SaveSampleUseCase
import com.omvp.domain.interactor.impl.GetSampleListUseCaseImpl
import com.omvp.domain.interactor.impl.GetSampleUseCaseImpl
import com.omvp.domain.interactor.impl.RemoveSampleUseCaseImpl
import com.omvp.domain.interactor.impl.SaveSampleUseCaseImpl

import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {

    @Binds
    @PerActivity
    internal abstract fun getSampleUseCase(repository: GetSampleUseCaseImpl): GetSampleUseCase

    @Binds
    @PerActivity
    internal abstract fun getSampleListUseCase(repository: GetSampleListUseCaseImpl): GetSampleListUseCase

    @Binds
    @PerActivity
    internal abstract fun removeSampleUseCase(repository: RemoveSampleUseCaseImpl): RemoveSampleUseCase

    @Binds
    @PerActivity
    internal abstract fun saveSampleUseCase(repository: SaveSampleUseCaseImpl): SaveSampleUseCase

}
