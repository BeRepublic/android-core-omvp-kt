package com.omvp.domain.interactor

import io.reactivex.Completable

interface RegisterDeviceUseCase {

    fun execute(): Completable

}
