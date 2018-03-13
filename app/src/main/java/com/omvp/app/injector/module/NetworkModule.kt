package com.omvp.app.injector.module

import android.content.Context

import com.google.gson.Gson
import com.omvp.app.BuildConfig
import com.omvp.app.R
import com.omvp.data.network.gateway.AppGatewayCredentials
import com.omvp.data.network.gateway.AppGateway
import com.omvp.data.network.gateway.retrofit.AppRetrofitGatewayCredentialsImpl
import com.omvp.data.network.gateway.retrofit.AppRetrofitGatewayImpl
import com.omvp.data.network.gateway.retrofit.callAdapter.RxErrorHandlingCallAdapterFactory
import com.omvp.data.network.gateway.retrofit.interceptor.CredentialsInterceptor
import com.omvp.data.network.gateway.retrofit.interceptor.HttpCacheInterceptor
import com.omvp.data.network.gateway.retrofit.interceptor.HttpLocaleInterceptor
import com.omvp.data.network.gateway.retrofit.service.AppCredentialsRetrofitService
import com.omvp.data.network.gateway.retrofit.service.AppRetrofitService
import com.omvp.domain.repository.CredentialsRepository
import com.omvp.domain.repository.LocaleRepository

import java.util.concurrent.TimeUnit

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
object NetworkModule {

    private const val HTTP_CACHE_MAX_AGE = 60 * 10              // read from cache for 10 minutes
    private const val HTTP_CACHE_MAX_STALE = 60 * 60 * 24 * 28  // tolerate 4-weeks stale
    private const val TIMEOUT = 35                              // 30 sec
    private const val CONNECT_TIMEOUT = 15                      // 10 sec

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptorLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    // Interceptors ================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun cacheInterceptor(context: Context): HttpCacheInterceptor {
        val httpCacheInterceptor = HttpCacheInterceptor(context)
        httpCacheInterceptor.cacheMaxAge = HTTP_CACHE_MAX_AGE
        httpCacheInterceptor.cacheMaxStale = HTTP_CACHE_MAX_STALE
        return httpCacheInterceptor
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun localeInterceptor(repository: LocaleRepository): HttpLocaleInterceptor {
        return HttpLocaleInterceptor(repository)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptor(level: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = level
        return loggingInterceptor
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun credentialsInterceptor(repository: CredentialsRepository): CredentialsInterceptor {
        return CredentialsInterceptor(repository)
    }

    // OKHttpClient ================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpClient(loggingInterceptor: HttpLoggingInterceptor, httpCacheInterceptor: HttpCacheInterceptor,
                            httpLocaleInterceptor: HttpLocaleInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(httpCacheInterceptor)
                .addInterceptor(httpLocaleInterceptor)
                .cache(cache)
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("credentials")
    internal fun credentialsHttpClient(loggingInterceptor: HttpLoggingInterceptor, credentialsInterceptor: CredentialsInterceptor,
                                       httpLocaleInterceptor: HttpLocaleInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(credentialsInterceptor)
                .addInterceptor(httpLocaleInterceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
    }

    // Retrofit ====================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun retrofit(context: Context, httpClient: OkHttpClient,
                          @Named("excludeFieldsWithoutExposeAnnotation") gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_domain))
                .client(httpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named("credentials")
    internal fun credentialsRetrofit(context: Context, @Named("credentials") httpClient: OkHttpClient,
                                     @Named("excludeFieldsWithoutExposeAnnotation") gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_domain))
                .client(httpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    // RetrofitService =============================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun appRetrofitService(retrofit: Retrofit): AppRetrofitService {
        return retrofit.create(AppRetrofitService::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun appCredentialsRetrofitService(@Named("credentials") retrofit: Retrofit): AppCredentialsRetrofitService {
        return retrofit.create(AppCredentialsRetrofitService::class.java)
    }

    // Gateway =====================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun appGateway(context: Context, service: AppRetrofitService): AppGateway {
        return AppRetrofitGatewayImpl(context, service)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun appCredentialsGateway(context: Context, service: AppCredentialsRetrofitService): AppGatewayCredentials {
        return AppRetrofitGatewayCredentialsImpl(context, service)
    }

}
