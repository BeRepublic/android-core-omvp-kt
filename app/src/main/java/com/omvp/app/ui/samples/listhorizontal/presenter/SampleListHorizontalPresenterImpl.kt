package com.omvp.app.ui.samples.listhorizontal.presenter


import android.net.Uri
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableCompletableObserver
import com.omvp.app.base.reactivex.BaseDisposableMaybeObserver
import com.omvp.app.model.SampleModel
import com.omvp.app.model.mapper.SampleModelDataMapper
import com.omvp.app.ui.samples.listhorizontal.adapter.SampleListAdapter
import com.omvp.app.ui.samples.listhorizontal.view.SampleListHorizontalView
import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleListUseCase
import com.omvp.domain.interactor.RemoveSampleUseCase
import com.omvp.domain.interactor.SaveSampleUseCase

import org.threeten.bp.LocalDateTime

import java.util.ArrayList
import java.util.UUID

import javax.inject.Inject

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class SampleListHorizontalPresenterImpl @Inject
constructor(sampleListHorizontalView: SampleListHorizontalView) : BasePresenter<SampleListHorizontalView>(sampleListHorizontalView),
        SampleListHorizontalPresenter, SampleListAdapter.AdapterCallback {

    @Inject
    internal lateinit var mGetSampleListUseCase: GetSampleListUseCase
    @Inject
    internal lateinit var mSaveSampleUseCase: SaveSampleUseCase
    @Inject
    internal lateinit var mRemoveSampleUseCase: RemoveSampleUseCase
    @Inject
    internal lateinit var mSampleModelDataMapper: SampleModelDataMapper

    private val mSampleDomainList = ArrayList<Any>()

    override fun onViewLoaded() {
        super.onViewLoaded()

        loadSampleList()
    }

    override fun sampleItemSelected(position: Int, sharedView: View) {
        sampleItemSelected(mSampleDomainList[position] as SampleDomain, sharedView)
    }

    override fun sampleHorizontalItemSelected(position: Int, horizontalListPosition: Int, sharedView: View) {
        val horizontalList = mSampleDomainList[position] as List<SampleDomain>
        sampleItemSelected(horizontalList[horizontalListPosition], sharedView)
    }

    override fun sampleItemDeleteSelected(position: Int) {
        removeItem(position)
    }

    override fun onAddSampleItemSelected() {
        addItem()
    }

    private fun loadSampleList() {
        mDisposableManager.add(Maybe.zip(
                retrieveMainList(),
                retrieveFirstHorizontalList(),
                retrieveSecondHorizontalList(),
                Function3<List<SampleDomain>, List<SampleDomain>, List<SampleDomain>, List<Any>> { mainList, firstHorizontal, secondHorizontal ->
                    mSampleDomainList.addAll(mainList)
                    mSampleDomainList.add(3, firstHorizontal)
                    mSampleDomainList.add(7, secondHorizontal)

                    val finalObjects = ArrayList<Any>()
                    finalObjects.addAll(mSampleModelDataMapper.transform(mainList))
                    finalObjects.add(3, mSampleModelDataMapper.transform(firstHorizontal))
                    finalObjects.add(7, mSampleModelDataMapper.transform(secondHorizontal))
                    finalObjects
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : BaseDisposableMaybeObserver<List<Any>>(mContext) {
                    override fun onStart() {
                        showProgress()
                    }

                    override fun onSuccess(sampleModelList: List<Any>) {
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

    private fun retrieveMainList(): Maybe<List<SampleDomain>> {
        return mGetSampleListUseCase.execute().subscribeOn(Schedulers.io())
    }

    private fun retrieveFirstHorizontalList(): Maybe<List<SampleDomain>> {
        return mGetSampleListUseCase.execute().subscribeOn(Schedulers.io())
    }

    private fun retrieveSecondHorizontalList(): Maybe<List<SampleDomain>> {
        return mGetSampleListUseCase.execute().subscribeOn(Schedulers.io())
    }

    private fun removeItem(position: Int) {
        val sampleDomain = mSampleDomainList[position] as SampleDomain
        mDisposableManager.add(mRemoveSampleUseCase.execute(sampleDomain.id)
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
                        mSampleDomainList.remove(sampleDomain)
                        drawRemoveAnimation(position)
                    }
                }))
    }

    private fun addItem() {

        val sampleDomain = SampleDomain(
                UUID.randomUUID().toString(),
                "item " + mSampleDomainList.size + 1,
                Uri.parse("https://www.google.com"),
                LocalDateTime.now(),
                R.mipmap.ic_launcher_round
        )

        mDisposableManager.add(mSaveSampleUseCase.execute(sampleDomain)
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
                        mSampleDomainList.add(sampleDomain)
                        drawAddAnimation(mSampleModelDataMapper.transform(sampleDomain))
                    }
                }))
    }

    private fun drawAddAnimation(model: SampleModel) {
        mView?.drawAddAnimation(model)
    }

    private fun drawRemoveAnimation(position: Int) {
        mView?.drawRemoveAnimation(position)
    }

    private fun drawSampleList(sampleModelList: List<Any>) {
        mView?.drawSampleList(sampleModelList)
    }

    private fun showEmptyView() {
        mView?.showEmptyView()
    }

    private fun sampleItemSelected(sampleDomain: SampleDomain, sharedView: View) {
        mView?.onSampleItemSelected(sampleDomain, sharedView)
    }
}
