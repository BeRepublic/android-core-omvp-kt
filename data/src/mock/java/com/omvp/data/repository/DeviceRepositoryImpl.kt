package com.omvp.data.repository

import android.content.Context

import com.google.firebase.iid.FirebaseInstanceId
import com.omvp.data.StaticRepository
import com.omvp.domain.repository.DeviceRepository
import com.raxdenstudios.commons.util.Utils

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.annotations.NonNull

/**
 * Created by Angel on 27/03/2018.
 */

class DeviceRepositoryImpl @Inject
internal constructor(private val context: Context) : DeviceRepository {

    init {
        StaticRepository.init()
    }

    override fun register(): Completable {
        // TODO: implement if server support
        return Completable.complete()
    }

    override fun checkApplicationVersion(): Single<Boolean> {
        return Single.just(true)
    }

    override fun getUrbanAirshipChannelId(): Single<String>? {
        return null
    }

    override fun getFirebaseToken(): Single<String> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    if (FirebaseInstanceId.getInstance().token.isNullOrEmpty()){
                        emitter.onSuccess(FirebaseInstanceId.getInstance().token!!)
                    } else {
                        throw Exception()
                    }
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun getSecureAndroidId(): Single<String> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(Utils.getSecureAndroidId(context))
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun getVersionName(): Single<String> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(Utils.getVersionName(context))
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun getVersionCode(): Single<Int> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(Utils.getVersionCode(context))
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun getDeviceModel(): Single<String> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(Utils.getDeviceModel())
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

}
