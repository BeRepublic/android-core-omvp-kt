package com.omvp.domain.repository

import java.util.Locale

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LocaleRepository : Repository {

    fun retrieveList(): Single<List<Locale>>

    fun persist(locale: Locale): Completable

    fun retrieve(): Single<Locale>

}
