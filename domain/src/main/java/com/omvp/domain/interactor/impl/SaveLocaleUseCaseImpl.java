package com.omvp.domain.interactor.impl;

import com.omvp.domain.interactor.SaveLocaleUseCase;
import com.omvp.domain.repository.LocaleRepository;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;

public class SaveLocaleUseCaseImpl extends BaseUseCaseImpl<LocaleRepository> implements SaveLocaleUseCase {

    @Inject
    SaveLocaleUseCaseImpl(LocaleRepository repository) {
        super(repository);
    }

    @Override
    public Completable execute(Locale locale) {
        return getRepository().persist(locale);
    }

}
