package com.omvp.data.repository

import com.omvp.domain.Credentials
import com.omvp.domain.repository.CredentialsRepository
import com.raxdenstudios.preferences.AdvancedPreferences

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.annotations.NonNull

class CredentialsRepositoryImpl
@Inject
internal constructor(private val preferences: AdvancedPreferences) :
        CredentialsRepository {

    override fun persist(credentials: Credentials): Completable {
        return Completable.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    preferences.put(Credentials::class.java.simpleName, credentials)
                    preferences.commit()
                    emitter.onComplete()
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun retrieve(): Single<Credentials> {
        return Single.create { emitter ->
            try {
                val credentials = preferences.get(Credentials::class.java.simpleName, Credentials::class.java, Credentials())
                emitter.onSuccess(credentials)
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

}
