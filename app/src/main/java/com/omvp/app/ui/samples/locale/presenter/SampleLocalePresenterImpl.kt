package com.omvp.app.ui.samples.locale.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableCompletableObserver
import com.omvp.app.base.reactivex.BaseDisposableSingleObserver
import com.omvp.app.ui.samples.locale.view.SampleLocaleView
import com.omvp.app.util.LocaleHelper
import com.omvp.domain.interactor.GetLocaleListUseCase
import com.omvp.domain.interactor.GetLocaleUseCase
import com.omvp.domain.interactor.SaveLocaleUseCase

import java.util.ArrayList
import java.util.Locale

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class SampleLocalePresenterImpl @Inject
constructor(sampleLocaleView: SampleLocaleView) : BasePresenter<SampleLocaleView>(sampleLocaleView), SampleLocalePresenter {

    @Inject
    internal lateinit var mGetLocaleUseCase: GetLocaleUseCase
    @Inject
    internal lateinit var mGetLocaleListUseCase: GetLocaleListUseCase
    @Inject
    internal lateinit var mSaveLocaleUseCase: SaveLocaleUseCase

    private var mLocale: Locale? = null
    private var mLocaleList: List<Locale>? = null

    override fun onViewLoaded() {
        super.onViewLoaded()

        loadLocale()
    }

    override fun changeLocale() {
        mDisposableManager.add(mGetLocaleListUseCase.execute()
                .map { localeList ->
                    mLocaleList = localeList
                    localeList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableSingleObserver<List<Locale>>(mContext) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onStart() {
                        showProgress()
                    }

                    override fun onSuccess(localeList: List<Locale>) {
                        hideProgress()
                        drawLocaleSelector(localeList)
                    }
                }))
    }

    override fun localeSelected(position: Int) {
        mLocale = mLocaleList!![position]
        mDisposableManager.add(mSaveLocaleUseCase.execute(mLocale!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableCompletableObserver(mContext) {
                    override fun onStart() {
                        showProgress()
                    }

                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onComplete() {
                        hideProgress()
                        LocaleHelper.updateConfiguration(mResources, mLocale!!)
                        drawLocale(mLocale!!)
                    }
                }))
    }

    private fun loadLocale() {
        mDisposableManager.add(mGetLocaleUseCase.execute()
                .map { locale ->
                    mLocale = locale
                    locale
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableSingleObserver<Locale>(mContext) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onStart() {
                        showProgress()
                    }

                    override fun onSuccess(locale: Locale) {
                        hideProgress()
                        drawLocale(locale)
                    }
                }))
    }

    private fun drawLocale(locale: Locale) {
        mView?.drawLocale(locale.toString())
    }

    private fun drawLocaleSelector(localeList: List<Locale>) {
        var selection = -1
        val localeStringList = ArrayList<String>()
        for ((count, locale) in localeList.withIndex()) {
            if (mLocale!!.language == locale.language) {
                selection = count
            }
            localeStringList.add(locale.toString())
        }
        mView?.drawLocaleSelector(localeStringList, selection)
    }

}
