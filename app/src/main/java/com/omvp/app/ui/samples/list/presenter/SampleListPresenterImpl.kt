package com.omvp.app.ui.samples.list.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableMaybeObserver
import com.omvp.app.model.SampleModel
import com.omvp.app.model.mapper.SampleModelDataMapper
import com.omvp.app.ui.samples.list.adapter.SampleListAdapter
import com.omvp.app.ui.samples.list.view.SampleListView
import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleListUseCase

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SampleListPresenterImpl
@Inject internal constructor(sampleListView: SampleListView) :
        BasePresenter<SampleListView>(sampleListView),
        SampleListPresenter, SampleListAdapter.AdapterCallback {

    @Inject
    internal lateinit var mGetSampleListUseCase: GetSampleListUseCase
    @Inject
    internal lateinit var mSampleModelDataMapper: SampleModelDataMapper

    private var mSampleDomainList: List<SampleDomain> = ArrayList()

    override fun onViewLoaded() {
        super.onViewLoaded()

        loadSampleList()
    }

    override fun sampleItemSelected(position: Int) {
        sampleItemSelected(mSampleDomainList[position])
    }

    private fun loadSampleList() {
        mDisposableManager.add(mGetSampleListUseCase.execute()
                .map { sampleDomains ->
                    mSampleDomainList = sampleDomains
                    mSampleModelDataMapper.transform(sampleDomains)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : BaseDisposableMaybeObserver<List<SampleModel>>(mContext) {
                    override fun onStart() {
                        super.onStart()
                        showProgress()
                    }

                    override fun onSuccess(sampleModelList: List<SampleModel>) {
                        hideProgress()
                        drawSampleList(sampleModelList)
                    }

                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }

                    override fun onComplete() {
                        hideProgress()
                        showEmptyView()
                    }
                }))
    }

    private fun drawSampleList(sampleModelList: List<SampleModel>) {
        mView?.drawSampleList(sampleModelList)
    }

    private fun showEmptyView() {
        mView?.showEmptyView()
    }

    private fun sampleItemSelected(sampleDomain: SampleDomain) {
        mView?.onSampleItemSelected(sampleDomain)
    }
}
