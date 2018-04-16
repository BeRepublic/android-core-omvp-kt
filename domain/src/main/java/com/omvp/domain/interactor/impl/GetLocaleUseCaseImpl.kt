package com.omvp.domain.interactor.impl

import com.omvp.domain.interactor.GetLocaleUseCase
import com.omvp.domain.repository.LocaleRepository

import java.util.Locale

import javax.inject.Inject

import io.reactivex.Single

class GetLocaleUseCaseImpl @Inject
internal constructor(repository: LocaleRepository) : BaseUseCaseImpl<LocaleRepository>(repository), GetLocaleUseCase {

    override fun execute(): Single<Locale> {
        return repository.retrieve()
    }

}
