package com.omvp.domain.repository;

import com.raxdenstudios.cron.model.Cron;

import io.reactivex.Single;

public interface CronRepository extends Repository {

    Single<Cron> register(Cron cron);

}
