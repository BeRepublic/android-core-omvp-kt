package com.omvp.domain.interactor.impl

import com.omvp.domain.interactor.RegisterDeviceUseCase
import com.omvp.domain.repository.DeviceRepository

import javax.inject.Inject

import io.reactivex.Completable

class RegisterDeviceUseCaseImpl @Inject
internal constructor(repository: DeviceRepository) : BaseUseCaseImpl<DeviceRepository>(repository), RegisterDeviceUseCase {

    override fun execute(): Completable {
        return repository.register()
    }

}
