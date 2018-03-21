package com.omvp.data.network.gateway.retrofit.service

import com.omvp.data.entity.SampleEntity

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppRetrofitService {

    // Sample Flow

    @GET("sample/{id}")
    fun retrieve(
            @Path("id") id: String
    ): Single<SampleEntity>

    @GET("sample/list")
    fun retrieveList(): Maybe<List<SampleEntity>>

    @POST("sample")
    fun persist(
            @Body sampleEntity: SampleEntity
    ): Completable

    @DELETE("sample/{id}")
    fun remove(
            @Path("id") id: String
    ): Completable

}
