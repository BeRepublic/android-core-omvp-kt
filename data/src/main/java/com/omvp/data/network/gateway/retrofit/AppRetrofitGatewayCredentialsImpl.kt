package com.omvp.data.network.gateway.retrofit

import android.content.Context

import com.omvp.data.entity.SampleEntity
import com.omvp.data.network.gateway.AppGatewayCredentials
import com.omvp.data.network.gateway.retrofit.service.AppCredentialsRetrofitService

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class AppRetrofitGatewayCredentialsImpl(
        private val context: Context,
        private val service: AppCredentialsRetrofitService
) : AppGatewayCredentials {

    override fun retrieve(id: String): Single<SampleEntity> {
        return service.retrieve(id)
    }

    override fun retrieveList(): Maybe<List<SampleEntity>> {
        return service.retrieveList()
    }

    override fun persist(sampleEntity: SampleEntity): Completable {
        return service.persist(sampleEntity)
    }

    override fun remove(id: String): Completable {
        return service.remove(id)
    }

}
