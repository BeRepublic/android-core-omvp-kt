package com.omvp.domain.interactor.impl

import com.omvp.domain.interactor.SaveLocaleUseCase
import com.omvp.domain.repository.LocaleRepository

import java.util.Locale

import javax.inject.Inject

import io.reactivex.Completable

class SaveLocaleUseCaseImpl @Inject
internal constructor(repository: LocaleRepository) : BaseUseCaseImpl<LocaleRepository>(repository), SaveLocaleUseCase {

    override fun execute(locale: Locale): Completable {
        return repository.persist(locale)
    }

}
