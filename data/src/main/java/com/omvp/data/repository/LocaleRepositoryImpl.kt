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

    override fun retrieveList(): Maybe<List<Locale>> {
        return Maybe.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    val localeList = retrieveLocaleList()
                    if (!localeList.isEmpty()) {
                        emitter.onSuccess(localeList)
                    } else {
                        emitter.onComplete()
                    }
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
                    persistLocale(locale)
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
                    emitter.onSuccess(retrieveLocale())
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    private fun retrieveLocale(): Locale {
        var locale: Locale? = null
        val language = preferences.get(PREF_LANGUAGE_SELECTED, "")
        if (!TextUtils.isEmpty(language)) {
            val values = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            locale = Locale(values[0], values[1])
        } else {
            val defaultDeviceLocale = Locale.getDefault()
            val availableLocaleList = retrieveLocaleList()
            for (availableLocale in availableLocaleList) {
                if (defaultDeviceLocale.language == availableLocale.language) {
                    locale = defaultDeviceLocale
                }
            }
            if (locale == null) {
                locale = defaultLocale
            }
            preferences.put(PREF_LANGUAGE_SELECTED, locale.toString())
            preferences.commit()
        }
        return locale
    }

    private fun retrieveLocaleList(): List<Locale> {
        val localeList = ArrayList<Locale>()
        val languageList = preferences.get(PREF_AVAILABLE_LANGUAGE_LIST, HashSet())
        if (languageList.isEmpty()) {
            for (locale in availableLocaleList) {
                languageList.add(locale.toString())
            }
            preferences.put(PREF_AVAILABLE_LANGUAGE_LIST, languageList)
            preferences.commit()
        }
        for (language in languageList) {
            val values = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            localeList.add(Locale(values[0], values[1]))
        }
        return localeList
    }

    private fun persistLocale(locale: Locale) {
        preferences.put(PREF_LANGUAGE_SELECTED, locale.toString())
        preferences.commit()
    }

    companion object {

        private const val PREF_LANGUAGE_SELECTED = "pref_language_selected"
        private const val PREF_AVAILABLE_LANGUAGE_LIST = "pref_language_list"
    }

}
