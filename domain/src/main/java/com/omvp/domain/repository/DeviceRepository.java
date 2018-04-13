package com.omvp.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DeviceRepository extends Repository {

    /**
     * Register device in server to receive push notifications.
     * @return
     */
    Completable register();

    /**
     * Check if application version is last published.
     * @return
     */
    Single<Boolean> checkApplicationVersion();

    /**
     * Retrieve channelId from urbanAirship. This identifier is used to identify device in push
     * notificacions via urbanairship.
     * @return
     */
    Single<String> getUrbanAirshipChannelId();

    /**
     * Retrieve firebase token. This identifier is used to identify device in push notificacions
     * via firebase.
     * @return
     */
    Single<String> getFirebaseToken();

    /**
     * Retrieve uniqueID asociated to device. This id change every time when application is
     * installed o updated.
     * @return
     */
    Single<String> getSecureAndroidId();

    /**
     * Retrieve application version name
     * @return
     */
    Single<String> getVersionName();

    /**
     * Retrieve application version code
     * @return
     */
    Single<Integer> getVersionCode();

    /**
     * Retrieve device model info.
     * @return
     */
    Single<String> getDeviceModel();

}
