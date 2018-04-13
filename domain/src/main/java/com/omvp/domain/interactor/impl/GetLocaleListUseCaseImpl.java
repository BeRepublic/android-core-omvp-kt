package com.omvp.domain.interactor.impl;

import com.omvp.domain.interactor.GetLocaleListUseCase;
import com.omvp.domain.repository.LocaleRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetLocaleListUseCaseImpl extends BaseUseCaseImpl<LocaleRepository> implements GetLocaleListUseCase {

    @Inject
    GetLocaleListUseCaseImpl(LocaleRepository repository) {
        super(repository);
    }

    @Override
    public Single<List<Locale>> execute() {
        return getRepository().retrieveList();
    }

}
