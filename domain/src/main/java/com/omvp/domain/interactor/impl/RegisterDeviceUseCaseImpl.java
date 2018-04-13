package com.omvp.domain.interactor.impl;

import com.omvp.domain.interactor.RegisterDeviceUseCase;
import com.omvp.domain.repository.DeviceRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class RegisterDeviceUseCaseImpl extends BaseUseCaseImpl<DeviceRepository> implements RegisterDeviceUseCase {

    @Inject
    RegisterDeviceUseCaseImpl(DeviceRepository repository) {
        super(repository);
    }

    @Override
    public Completable execute() {
        return getRepository().register();
    }

}
