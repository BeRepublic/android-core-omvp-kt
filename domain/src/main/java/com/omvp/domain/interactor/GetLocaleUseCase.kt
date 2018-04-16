package com.omvp.domain.interactor

import java.util.Locale

import io.reactivex.Single

/**
 * Created by Angel on 05/09/2017.
 */

interface GetLocaleUseCase {

    fun execute(): Single<Locale>

}
