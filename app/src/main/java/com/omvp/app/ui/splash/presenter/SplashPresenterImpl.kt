package com.omvp.app.ui.splash.presenter

import android.os.Handler

import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableCompletableObserver
import com.omvp.app.ui.splash.view.SplashView
import com.omvp.commons.Constants

import java.util.concurrent.TimeUnit

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashPresenterImpl @Inject
internal constructor(splashView: SplashView) : BasePresenter<SplashView>(splashView), SplashPresenter {

    private var mProgress: Float = 0.toFloat()
    private var mHandler: Handler? = null

    private val mProgressRunnable = object : Runnable {
        override fun run() {
            if (mProgress < 100.0f) {
                showProgress(mProgress++)
                mHandler?.postDelayed(this, 50)
            } else {
                mHandler?.removeCallbacks(this)
            }
        }
    }

    override fun onViewLoaded() {
        super.onViewLoaded()
        prepareApplicationToLaunch()
    }

    override fun onDropView() {
        if (mHandler != null) {
            mHandler!!.removeCallbacks(mProgressRunnable)
            mHandler = null
        }
        super.onDropView()
    }

    // Support methods =============================================================================

    private fun prepareApplicationToLaunch() {
        mDisposableManager.add(makeTime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableCompletableObserver(mContext) {
                    override fun onStart() {
                        initProgressBar()
                        showProgress()
                    }

                    override fun onError(code: Int, title: String, description: String) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onComplete() {
                        hideProgress()
                        applicationReadyToLaunch()
                    }
                }))
    }

    private fun makeTime(): Completable {
        return Completable.timer(Constants.SPLASH_DELAY.toLong(), TimeUnit.MILLISECONDS)
    }

    private fun initProgressBar() {
        mHandler = Handler()
        mHandler!!.post(mProgressRunnable)
    }

    private fun applicationReadyToLaunch() {
        mView.applicationReadyToLaunch()
    }

}
