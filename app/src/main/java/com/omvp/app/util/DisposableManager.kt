package com.omvp.app.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Ángel Gómez on 23/02/2018.
 */
class DisposableManager {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    val isEmpty: Boolean
        get() = mCompositeDisposable.size() == 0

    fun dispose() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    fun add(disposable: Disposable?) {
        if (disposable != null) {
            mCompositeDisposable.add(disposable)
        }
    }

    fun remove(disposable: Disposable?) {
        if (disposable != null) {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
            mCompositeDisposable.remove(disposable)
        }
    }

    fun removeAll() {
        mCompositeDisposable.clear()
    }

}
