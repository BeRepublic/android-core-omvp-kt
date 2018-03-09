package com.omvp.app.base.reactivex

import android.app.Fragment
import android.content.Context

import com.omvp.app.util.ErrorManager

import io.reactivex.CompletableObserver
import io.reactivex.annotations.NonNull
import timber.log.Timber

/**
 * Created by Angel on 10/08/2017.
 */
abstract class BaseCompletableObserver : CompletableObserver {

    private lateinit var mErrorManager: ErrorManager

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