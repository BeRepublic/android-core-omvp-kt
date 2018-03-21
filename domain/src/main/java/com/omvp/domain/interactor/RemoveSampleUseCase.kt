package com.omvp.domain.interactor

import io.reactivex.Completable

/**
 * Created by Angel on 05/09/2017.
 */

interface RemoveSampleUseCase {

    fun execute(id: Long): Completable

}
