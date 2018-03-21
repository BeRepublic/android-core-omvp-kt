package com.omvp.data.network.gateway

import com.omvp.data.entity.SampleEntity

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface AppGatewayCredentials {

    // Sample Flow

    fun retrieve(id: String): Single<SampleEntity>

    fun retrieveList(): Maybe<List<SampleEntity>>

    fun persist(sampleEntity: SampleEntity): Completable

    fun remove(id: String): Completable

}
