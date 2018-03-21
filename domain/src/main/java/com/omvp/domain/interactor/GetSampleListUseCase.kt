package com.omvp.domain.interactor

import com.omvp.domain.SampleDomain

import io.reactivex.Maybe

/**
 * Created by Angel on 05/09/2017.
 */

interface GetSampleListUseCase {

    fun execute(): Maybe<List<SampleDomain>>

}
