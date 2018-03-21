package com.omvp.data.network.gateway.retrofit.interceptor


import android.text.TextUtils

import com.omvp.domain.repository.LocaleRepository

import java.io.IOException
import java.util.Locale

import io.reactivex.observers.DisposableSingleObserver
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class HttpLocaleInterceptor(private val localeRepository: LocaleRepository) : Interceptor {

    private var language: String = ""

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("[intercept] %s", chain.request().url().toString())
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header("Accept-Language", retrieveLanguage())
                .method(originalRequest.method(), originalRequest.body())

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun retrieveLanguage(): String {
        if (TextUtils.isEmpty(language)) {
            localeRepository.retrieve()
                    .subscribeWith(object : DisposableSingleObserver<Locale>() {
                        override fun onSuccess(locale: Locale) {
                            language = locale.toString().replace("_".toRegex(), "-")
                        }

                        override fun onError(e: Throwable) {

                        }
                    })
        }
        return language
    }

}
