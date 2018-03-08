package com.omvp.app.ui.home.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.home.view.HomeView

import javax.inject.Inject

class HomePresenterImpl @Inject
internal constructor(homeView: HomeView) : BasePresenter<HomeView>(homeView), HomePresenter
