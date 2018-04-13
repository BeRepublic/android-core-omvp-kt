package com.omvp.domain.interactor.impl;

import com.omvp.domain.interactor.GetLocaleUseCase;
import com.omvp.domain.repository.LocaleRepository;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetLocaleUseCaseImpl extends BaseUseCaseImpl<LocaleRepository> implements GetLocaleUseCase {

    @Inject
    GetLocaleUseCaseImpl(LocaleRepository repository) {
        super(repository);
    }

    @Override
    public Single<Locale> execute() {
        return getRepository().retrieve();
    }

}
