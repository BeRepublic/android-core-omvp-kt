package com.omvp.domain.interactor.impl

import com.omvp.domain.interactor.GetLocaleListUseCase
import com.omvp.domain.repository.LocaleRepository
import java.util.Locale

import javax.inject.Inject

import io.reactivex.Single

class GetLocaleListUseCaseImpl @Inject
internal constructor(repository: LocaleRepository) : BaseUseCaseImpl<LocaleRepository>(repository), GetLocaleListUseCase {

    override fun execute(): Single<List<Locale>> {
        return repository.retrieveList()
    }

}
