package com.omvp.app.ui.samples.list.presenter


import android.net.Uri
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableCompletableObserver
import com.omvp.app.base.reactivex.BaseDisposableMaybeObserver
import com.omvp.app.model.SampleModel
import com.omvp.app.model.mapper.SampleModelDataMapper
import com.omvp.app.ui.samples.list.adapter.SampleListAdapter
import com.omvp.app.ui.samples.list.view.SampleListView
import com.omvp.app.util.RecyclerDragHelper
import com.omvp.domain.SampleDomain
import com.omvp.domain.interactor.GetSampleListUseCase
import com.omvp.domain.interactor.RemoveSampleUseCase
import com.omvp.domain.interactor.SaveSampleUseCase
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import java.util.*
import javax.inject.Inject

class SampleListPresenterImpl
@Inject internal constructor(sampleListView: SampleListView) : BasePresenter<SampleListView>(sampleListView),
        SampleListPresenter,
        SampleListAdapter.AdapterCallback,
        RecyclerDragHelper.ActionCompletionContract,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject
    internal lateinit var mGetSampleListUseCase: GetSampleListUseCase
    @Inject
    internal lateinit var mSaveSampleUseCase: SaveSampleUseCase
    @Inject
    internal lateinit var mRemoveSampleUseCase: RemoveSampleUseCase
    @Inject
    internal lateinit var mSampleModelDataMapper: SampleModelDataMapper

    private var mSampleDomainList: MutableList<SampleDomain> = ArrayList()

    override fun onViewLoaded() {
        super.onViewLoaded()

        loadSampleList()
    }

    override fun sampleItemSelected(position: Int, sharedView: View) {
        sampleItemSelected(mSampleDomainList[position], sharedView)
    }

    override fun sampleItemDeleteSelected(position: Int) {
        removeItem(position)
    }

    override fun onAddSampleItemSelected() {
        addItem()
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        Collections.swap(mSampleDomainList, oldPosition, newPosition)
        drawViewMoved(oldPosition, newPosition)
    }

    override fun onViewSwiped(position: Int) {
        mSampleDomainList.removeAt(position)
        drawViewSwiped(position)
    }

    override fun onRefresh() {
        /*
         *   This code edits a random item just to check the
         *   efficiency of the Adapter update by DiffUtils.
        */
        mDisposableManager.add(Maybe.just(mSampleDomainList)
                .map { sampleDomains -> mSampleModelDataMapper.transform(sampleDomains) }
                .map({ sampleModels -> editRandomItem(sampleModels) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : BaseDisposableMaybeObserver<List<SampleModel>>(mContext) {
                    override fun onSuccess(sampleModelList: List<SampleModel>) {
                        hideProgress()
                        drawUpdatedItems(sampleModelList)
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

    private fun editRandomItem(items: MutableList<SampleModel>): List<SampleModel> {
        val max = items.size - 1
        val min = 0
        val random = Random()
        val index = random.nextInt(max - min + 1)

        val model = items[index]
        model.title = System.currentTimeMillis().toString()
        items[index] = model
        return items
    }

    private fun loadSampleList() {
        mDisposableManager.add(mGetSampleListUseCase.execute()
                .map { sampleDomains ->
                    mSampleDomainList.addAll(sampleDomains)
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

    private fun removeItem(position: Int) {
        val sampleDomain = mSampleDomainList[position]
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
                "item " + (mSampleDomainList.size + 1),
                Uri.parse("https://www.google.com"),
                LocalDateTime.now(),
                com.omvp.data.R.mipmap.ic_launcher_round
        )

        if (mSampleDomainList == null) {
            mSampleDomainList = ArrayList()
        }

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

    private fun drawUpdatedItems(updatedList: List<SampleModel>) {
        mView?.drawUpdatedItems(updatedList)
    }

    private fun sampleItemSelected(sampleDomain: SampleDomain, sharedView: View) {
        mView?.onSampleItemSelected(sampleDomain, sharedView)
    }

    private fun drawViewMoved(oldPosition: Int, newPosition: Int) {
        mView?.drawViewMoved(oldPosition, newPosition)
    }

    private fun drawViewSwiped(position: Int) {
        mView?.drawViewSwiped(position)
    }

    private fun drawSampleList(sampleModelList: List<SampleModel>) {
        mView?.drawSampleList(sampleModelList)
    }

    private fun showEmptyView() {
        mView?.showEmptyView()
    }
}
