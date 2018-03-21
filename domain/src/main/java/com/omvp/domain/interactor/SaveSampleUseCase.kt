package com.omvp.domain.interactor

import com.omvp.domain.SampleDomain

import io.reactivex.Completable

/**
 * Created by Angel on 05/09/2017.
 */

interface SaveSampleUseCase {

    fun execute(sampleDomain: SampleDomain): Completable

}
