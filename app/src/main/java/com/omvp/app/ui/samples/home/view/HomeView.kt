package com.omvp.app.ui.samples.home.view

import com.omvp.app.base.mvp.view.BaseView
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl

/**
 * Created by Angel on 21/02/2018.
 */
interface HomeView : BaseView {

    fun itemSelected(item: HomePresenterImpl.SampleItemModel)

    fun drawItems(itemList: List<HomePresenterImpl.SampleItemModel>)
}
