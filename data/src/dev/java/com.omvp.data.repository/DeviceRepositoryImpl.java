package com.omvp.data.repository;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.omvp.data.network.gateway.AppGateway;
import com.omvp.domain.repository.DeviceRepository;
import com.raxdenstudios.commons.util.Utils;
import com.urbanairship.UAirship;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Angel on 27/03/2018.
 */

public class DeviceRepositoryImpl implements DeviceRepository {

    private final Context context;
    private final AppGateway gateway;

    @Inject
    DeviceRepositoryImpl(Context context, AppGateway gateway) {
        this.context = context;
        this.gateway = gateway;
    }

    @Override
    public Completable register() {
        // TODO: implement if server support
        return Completable.complete();
    }

    @Override
    public Single<Boolean> checkApplicationVersion() {
        // TODO: implement if server support
        return Single.just(true);
    }

    @Override
    public Single<String> getFirebaseToken() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(FirebaseInstanceId.getInstance().getToken());
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<String> getUrbanAirshipChannelId() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(UAirship.shared().getPushManager().getChannelId());
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<String> getSecureAndroidId() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(Utils.getSecureAndroidId(context));
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<String> getVersionName() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(Utils.getVersionName(context));
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<Integer> getVersionCode() {
        return Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(Utils.getVersionCode(context));
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<String> getDeviceModel() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                try {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(Utils.getDeviceModel());
                    }
                } catch (Exception ex) {
                    emitter.onError(ex);
                }
            }
        });
    }

}
