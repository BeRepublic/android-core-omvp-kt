package com.omvp.app.base.reactivex

import android.content.Context
import androidx.fragment.app.Fragment
import com.omvp.app.util.ErrorManager
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableMaybeObserver
import timber.log.Timber

/**
 * Created by Angel on 10/08/2017.
 */
abstract class BaseDisposableMaybeObserver<T> : DisposableMaybeObserver<T> {

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
