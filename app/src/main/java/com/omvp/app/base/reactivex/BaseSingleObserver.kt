package com.omvp.app.base.reactivex

import android.app.Fragment
import android.content.Context

import com.omvp.app.util.ErrorManager

import io.reactivex.SingleObserver
import io.reactivex.annotations.NonNull
import timber.log.Timber

/**
 * Created by Ángel Gómez on 30/07/2017.
 */
abstract class BaseSingleObserver<T> : SingleObserver<T> {

    private var mErrorManager: ErrorManager

    constructor(context: Context) {
        mErrorManager = ErrorManager(context.resources)
    }

    constructor(fragment: Fragment) {
        mErrorManager = ErrorManager(fragment.resources)
    }

    override fun onError(@NonNull e: Throwable) {
        Timber.e(e)
        mErrorManager.parseError(e)
        onError(mErrorManager.code, mErrorManager.title, mErrorManager.message)
    }

    protected abstract fun onError(code: Int, title: String?, description: String?)

}
