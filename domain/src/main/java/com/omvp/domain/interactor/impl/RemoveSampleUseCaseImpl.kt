package com.omvp.domain.interactor.impl

import com.omvp.domain.interactor.RemoveSampleUseCase
import com.omvp.domain.repository.SampleRepository

import javax.inject.Inject

import io.reactivex.Completable

class RemoveSampleUseCaseImpl
@Inject
internal constructor(repository: SampleRepository) : BaseUseCaseImpl<SampleRepository>(repository),
        RemoveSampleUseCase {

    override fun execute(id: Long): Completable {
        return repository.remove(id)
    }

}
