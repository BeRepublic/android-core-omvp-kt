package com.omvp.data.repository

import android.text.TextUtils

import com.omvp.domain.repository.LocaleRepository
import com.raxdenstudios.preferences.AdvancedPreferences

import java.util.ArrayList
import java.util.HashSet
import java.util.Locale

import javax.inject.Inject
import javax.inject.Named

import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.MaybeOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.annotations.NonNull

class LocaleRepositoryImpl
@Inject
internal constructor(
        private val preferences: AdvancedPreferences,
        @param:Named("default") private val defaultLocale: Locale,
        private val availableLocaleList: Set<Locale>
) : LocaleRepository {

    override fun retrieveList(): Single<List<Locale>> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    emitter.onSuccess(ArrayList(availableLocaleList))
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun persist(locale: Locale): Completable {
        return Completable.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    preferences.put(PREF_LANGUAGE_SELECTED, locale.toString())
                    preferences.commit()
                    emitter.onComplete()
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    override fun retrieve(): Single<Locale> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    val locale: Locale
                    val language = preferences.get(PREF_LANGUAGE_SELECTED, "")
                    if (!TextUtils.isEmpty(language)) {
                        val values = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        locale = Locale(values[0], values[1])
                    } else {
                        locale = defaultLocale
                        preferences.put(PREF_LANGUAGE_SELECTED, locale.toString())
                        preferences.commit()
                    }
                    emitter.onSuccess(locale)
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    companion object {
        private const val PREF_LANGUAGE_SELECTED = "pref_language_selected"
    }

}
