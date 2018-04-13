package com.omvp.domain.interactor.impl

import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleUseCase
import com.omvp.domain.repository.SampleRepository

import javax.inject.Inject

import io.reactivex.Single

class GetSampleUseCaseImpl
@Inject
internal constructor(repository: SampleRepository) : BaseUseCaseImpl<SampleRepository>(repository),
        GetSampleUseCase {

    override fun execute(id: String): Single<SampleDomain> {
        return repository.retrieve(id)
    }

}
