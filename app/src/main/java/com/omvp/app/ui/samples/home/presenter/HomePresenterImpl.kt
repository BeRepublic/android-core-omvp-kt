package com.omvp.app.ui.samples.home.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.base.reactivex.BaseDisposableMaybeObserver
import com.omvp.app.ui.samples.home.adapter.HomeListAdapter
import com.omvp.app.ui.samples.home.view.HomeView
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomePresenterImpl
@Inject
constructor(homeView: HomeView) : BasePresenter<HomeView>(homeView), HomePresenter, HomeListAdapter.AdapterCallback {

    init {
        init()
    }

    override fun onViewLoaded() {
        super.onViewLoaded()
        loadData()
    }

    override fun itemSelected(position: Int) {
        itemSelected(sampleItemList[position])
    }

    private fun loadData() {
        mDisposableManager.add(Maybe.just<List<SampleItemModel>>(sampleItemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableMaybeObserver<List<SampleItemModel>>(mContext) {
                    override fun onStart() {
                        super.onStart()
                        showProgress()
                    }

                    override fun onSuccess(sampleItems: List<SampleItemModel>) {
                        hideProgress()
                        mView?.drawItems(sampleItems)
                    }

                    override fun onComplete() {
                        hideProgress()
                    }

                    override fun onError(code: Int, title: String?, description: String?) {
                        hideProgress()
                        showError(code, title, description)
                    }
                }))
    }

    private fun itemSelected(item: SampleItemModel) {
        mView?.itemSelected(item)
    }

    data class SampleItemModel(var type: String, var title: String)

    companion object {

        // HOME ITEMS
        internal const val VIEW = "VIEW"
        internal const val LIST = "LIST"
        internal const val HORIZONTAL_LIST = "HORIZONTAL_LIST"
        internal const val PAGER = "PAGER"
        internal const val MULTIPLE_FRAGMENTS = "MULTIPLE_FRAGMENTS"
        internal const val LOCATION = "LOCATION"
        internal const val PICTURE = "PICTURE"
        internal const val LOCALE = "LOCALE"
        internal const val VIBRATION = "VIBRATION"
        internal const val INPUT = "INPUT"
        internal const val SOCIAL = "SOCIAL"
        internal const val NOTICE_DIALOG = "NOTICE_DIALOG"
        internal const val BOTTOM_NAV = "BOTTOM_NAV"
        internal const val AUTH_PHONE = "AUTH_PHONE"

        private const val NUM_ITEMS = 14

        private val sampleItemList = ArrayList<SampleItemModel>(NUM_ITEMS)

        private fun init() {
            if (sampleItemList.isEmpty()) {
                sampleItemList.add(initSampleItem(VIEW, "A sample view"))
                sampleItemList.add(initSampleItem(LIST, "A sample list view"))
                sampleItemList.add(initSampleItem(HORIZONTAL_LIST, "A sample list view with nested horizontal list"))
                sampleItemList.add(initSampleItem(PAGER, "A sample pager view"))
                sampleItemList.add(initSampleItem(MULTIPLE_FRAGMENTS, "A sample view with multiple fragments"))
                sampleItemList.add(initSampleItem(LOCATION, "A sample view to show device location"))
                sampleItemList.add(initSampleItem(PICTURE, "A sample view to take or pick up photos"))
                sampleItemList.add(initSampleItem(LOCALE, "A sample view to change device language"))
                sampleItemList.add(initSampleItem(VIBRATION, "A sample view to use device vibration"))
                sampleItemList.add(initSampleItem(INPUT, "A view with input layouts"))
                sampleItemList.add(initSampleItem(SOCIAL, "A view with socials connection"))
                sampleItemList.add(initSampleItem(NOTICE_DIALOG, "A view with Notice Dialog"))
                sampleItemList.add(initSampleItem(BOTTOM_NAV, "A view with a bottom bar navigation"))
                sampleItemList.add(initSampleItem(AUTH_PHONE, "A view with check phone number validation"))
            }
        }

        private fun initSampleItem(type: String, title: String) = SampleItemModel(type, title)
    }
}
