package com.omvp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface DeviceRepository : Repository {

    /**
     * Retrieve channelId from urbanAirship. This identifier is used to identify device in push
     * notificacions via urbanairship.
     * @return
     */
    fun getUrbanAirshipChannelId(): Single<String>?

    /**
     * Retrieve firebase token. This identifier is used to identify device in push notificacions
     * via firebase.
     * @return
     */
    fun getFirebaseToken(): Single<String>

    /**
     * Retrieve uniqueID asociated to device. This id change every time when application is
     * installed o updated.
     * @return
     */
    fun getSecureAndroidId(): Single<String>

    /**
     * Retrieve application version name
     * @return
     */
    fun getVersionName(): Single<String>

    /**
     * Retrieve application version code
     * @return
     */
    fun getVersionCode(): Single<Int>

    /**
     * Retrieve device model info.
     * @return
     */
    fun getDeviceModel(): Single<String>

    /**
     * Register device in server to receive push notifications.
     * @return
     */
    fun register(): Completable

    /**
     * Check if application version is last published.
     * @return
     */
    fun checkApplicationVersion(): Single<Boolean>

}
