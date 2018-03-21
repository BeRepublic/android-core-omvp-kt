package com.omvp.domain.interactor.impl

import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleListUseCase
import com.omvp.domain.repository.SampleRepository

import javax.inject.Inject

import io.reactivex.Maybe

class GetSampleListUseCaseImpl
@Inject
internal constructor(repository: SampleRepository) : BaseUseCaseImpl<SampleRepository>(repository),
        GetSampleListUseCase {

    override fun execute(): Maybe<List<SampleDomain>> {
        return repository.retrieveList()
    }

}
