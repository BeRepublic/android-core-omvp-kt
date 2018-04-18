package com.omvp.app.ui.samples.bottomnavigation.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationView

abstract class BottomNavigationPresenterImpl<TView : BottomNavigationView>(view: TView) :
        BasePresenter<TView>(view), BottomNavigationPresenter
