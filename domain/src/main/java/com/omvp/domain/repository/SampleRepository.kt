package com.omvp.domain.repository

import com.omvp.domain.SampleDomain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Angel on 15/02/2018.
 */

interface SampleRepository : Repository {

    fun retrieve(id: String): Single<SampleDomain>

    fun retrieveList(): Maybe<List<SampleDomain>>

    fun persist(sampleDomain: SampleDomain): Completable

    fun remove(id: String): Completable

}
