package com.omvp.data.repository;

import com.omvp.domain.repository.CronRepository;
import com.raxdenstudios.cron.CronHandler;
import com.raxdenstudios.cron.model.Cron;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Angel on 27/03/2018.
 */

public class CronRepositoryImpl implements CronRepository {

    private final CronHandler cronHandler;

    @Inject
    CronRepositoryImpl(CronHandler cronHandler) {
        this.cronHandler = cronHandler;
    }

    @Override
    public Single<Cron> register(final Cron cron) {
        return Single.create(new SingleOnSubscribe<Cron>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Cron> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        cronHandler.start(cron);
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

}
