package com.omvp.app.ui.samples.bottomnavigation.view

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationPresenter

abstract class BottomNavigationFragment<TPresenter : BottomNavigationPresenter, TCallback : BottomNavigationFragment.BottomNavigationFragmentCallback> :
        BaseViewFragment<TPresenter, TCallback>(), BottomNavigationView {

    interface BottomNavigationFragmentCallback : BaseViewFragmentCallback

}
