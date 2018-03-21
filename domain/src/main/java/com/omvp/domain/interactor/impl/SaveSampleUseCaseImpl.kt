package com.omvp.domain.interactor.impl

import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.SaveSampleUseCase
import com.omvp.domain.repository.SampleRepository

import javax.inject.Inject

import io.reactivex.Completable

class SaveSampleUseCaseImpl
@Inject
internal constructor(repository: SampleRepository) : BaseUseCaseImpl<SampleRepository>(repository),
        SaveSampleUseCase {

    override fun execute(sampleDomain: SampleDomain): Completable {
        return repository.persist(sampleDomain)
    }

}
