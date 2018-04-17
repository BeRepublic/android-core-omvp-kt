package com.omvp.data.repository

import com.omvp.data.StaticRepository
import com.omvp.domain.SampleDomain
import com.omvp.domain.repository.SampleRepository

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class SampleRepositoryImpl @Inject
internal constructor() : SampleRepository {

    init {
        StaticRepository.init()
    }

    override fun retrieve(id: String): Single<SampleDomain> {
        return Single.just(StaticRepository.sampleDomainList[id])
    }

    override fun retrieveList(): Maybe<List<SampleDomain>> {
        val list = ArrayList(StaticRepository.sampleDomainList.values)
        return Maybe.just(list)
    }

    override fun persist(sampleDomain: SampleDomain): Completable {
        StaticRepository.sampleDomainList[sampleDomain.id] = sampleDomain
        return Completable.complete()
    }

    override fun remove(id: String): Completable {
        //        StaticRepository.sampleDomainList.remove(id);
        return Completable.complete()
    }

}
