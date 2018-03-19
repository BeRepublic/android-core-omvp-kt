package com.omvp.app.util

import android.content.res.Resources
import android.text.TextUtils

import com.omvp.app.R
import com.omvp.commons.AppException
import com.omvp.data.network.gateway.retrofit.exception.RetrofitException

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import io.reactivex.exceptions.CompositeException
import lombok.Data
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Angel on 23/11/2017.
 */
class ErrorManager(private val resources: Resources) {

    var code: Int = 0
    var title: String? = null
    var message: String? = null

    init {
        code = 0
        title = resources.getString(R.string.unespected_error_title)
        message = resources.getString(R.string.unespected_error_message)
    }

    fun parseError(throwable: Throwable) {
        if (throwable is CompositeException) {
            val compositeException = throwable
            if (!throwable.exceptions.isEmpty()) {
                for (childThrowable in throwable.exceptions) {
                    parseError(childThrowable)
                }
            }
        } else if (throwable is RetrofitException) {
            parseError(throwable)
        } else if (throwable is AppException) {
            parseError(throwable)
        }
    }

    fun parseError(exception: RetrofitException) {
        val url = exception.url
        val response = exception.response
        val cause = exception.cause
        code = response?.code() ?: 0
        title = resources.getString(R.string.unespected_error_title)
        message = resources.getString(R.string.unespected_error_message)
        if (cause is ConnectException) {
            message = resources.getString(R.string.unespected_timeout_message)
        } else if (cause is SocketTimeoutException) {
            message = resources.getString(R.string.unespected_timeout_message)
        } else if (cause is UnknownHostException) {
            message = resources.getString(R.string.unespected_timeout_message)
        } else {
            try {
                val errorMessage = exception.getErrorBodyAs(String::class.java)
                if (!TextUtils.isEmpty(errorMessage)) {
                    val jsonError = JSONObject(errorMessage)
                    try {
                        if (jsonError.has("code")) {
                            code = jsonError.getInt("code")
                            if (jsonError.has("message") && !TextUtils.isEmpty(jsonError.getString("message"))) {
                                message = jsonError.getString("message")
                            } else if (jsonError.has("raw") && !TextUtils.isEmpty(jsonError.getString("raw"))) {
                                message = jsonError.getString("raw")
                            }
                        }
                    } catch (e: JSONException) {
                        Timber.e(e)
                    }

                }
            } catch (e: JSONException) {
                Timber.e(e)
            } catch (e: IOException) {
                Timber.e(e)
            }

        }
        Timber.e("$url -  $code $title $message")
    }

    fun parseError(exception: AppException) {
        code = exception.code
        title = resources.getString(R.string.unespected_error_title)
        message = exception.message
    }

}
