package com.omvp.app.ui.ktsamples.ktsample.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.model.mapper.SampleModelDataMapper
import com.omvp.app.ui.ktsamples.ktsample.view.SampleView
import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleListUseCase
import com.omvp.domain.interactor.GetSampleUseCase

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.schedulers.Schedulers

class SamplePresenterImpl                               // name of the class
@Inject internal constructor(sampleView: SampleView) :  // constructor inlined with 'super(sampleView)'
        BasePresenter<SampleView>(sampleView),          // extend
        SamplePresenter {                               // implementations

    @Inject
    internal lateinit var mGetSampleUseCase: GetSampleUseCase
    @Inject
    internal lateinit var mGetSampleListUseCase: GetSampleListUseCase
    @Inject
    internal lateinit var mGetSampleListUseCase2: GetSampleListUseCase
    @Inject
    internal lateinit var mSampleModelDataMapper: SampleModelDataMapper

    override fun onViewLoaded() {
        super.onViewLoaded()

        mGetSampleListUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableMaybeObserver<List<SampleDomain>>() {
                    override fun onSuccess(sampleDomains: List<SampleDomain>) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

}
