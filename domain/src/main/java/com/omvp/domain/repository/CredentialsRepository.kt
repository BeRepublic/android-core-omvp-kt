package com.omvp.domain.repository

import com.omvp.domain.Credentials

import io.reactivex.Completable
import io.reactivex.Single

interface CredentialsRepository : Repository {

    fun persist(credentials: Credentials): Completable

    fun retrieve(): Single<Credentials>

}
