package com.omvp.domain.repository

import com.raxdenstudios.cron.model.Cron

import io.reactivex.Single

interface CronRepository : Repository {

    fun register(cron: Cron): Single<Cron>

}
