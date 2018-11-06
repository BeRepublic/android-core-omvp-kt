package com.omvp.app.ui.samples.detail.presenter

import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableSingleObserver
import com.omvp.app.ui.samples.detail.view.SampleDetailView
import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SampleDetailPresenterImpl @Inject
constructor(sampleDetailView: SampleDetailView) : BasePresenter<SampleDetailView>(sampleDetailView), SampleDetailPresenter {

    @Inject
    internal lateinit var mGetSampleUseCase: GetSampleUseCase

    private var mSampleId: String = ""

    override fun onViewLoaded() {
        super.onViewLoaded()
        loadSample()
    }

    override fun setSampleId(string: String?) {
        mSampleId = string ?: ""
    }

    private fun loadSample() {
        mDisposableManager.add(mGetSampleUseCase.execute(mSampleId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : BaseDisposableSingleObserver<SampleDomain>(mContext) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onStart() {
                        showProgress()
                    }

                    override fun onSuccess(sampleDomain: SampleDomain) {
                        hideProgress()
                        drawSampleItem(sampleDomain)
                    }
                }))
    }

    private fun drawSampleItem(sampleDomain: SampleDomain) {
        mView?.drawTitle(sampleDomain.title)
        mView?.drawImage(sampleDomain.imageResId)
    }

}
