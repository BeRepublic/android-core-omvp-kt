package com.omvp.data.network.gateway.retrofit.interceptor

import android.text.TextUtils

import com.omvp.domain.Credentials
import com.omvp.domain.repository.CredentialsRepository
import com.raxdenstudios.commons.util.MediaType

import java.io.IOException

import io.reactivex.observers.DisposableSingleObserver
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CredentialsInterceptor(private val credentialsRepository: CredentialsRepository) : Interceptor {

    private var authorization: String = ""

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header("Authorization", retrieveAuthorization())
                .header("Content-Type", MediaType.APPLICATION_JSON.toString())
                .method(originalRequest.method(), originalRequest.body())

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun retrieveAuthorization(): String {
        if (TextUtils.isEmpty(authorization)) {
            credentialsRepository.retrieve()
                    .subscribeWith(object : DisposableSingleObserver<Credentials>() {
                        override fun onSuccess(credentials: Credentials) {
                            authorization = "Bearer " + credentials.accessToken
                        }

                        override fun onError(e: Throwable) {

                        }
                    })
        }
        return authorization
    }

}
