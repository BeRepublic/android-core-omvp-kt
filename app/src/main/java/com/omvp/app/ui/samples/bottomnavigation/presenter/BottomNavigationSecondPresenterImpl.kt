package com.omvp.app.ui.samples.bottomnavigation.presenter


import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationSecondFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationSecondView

import javax.inject.Inject

class BottomNavigationSecondPresenterImpl @Inject
constructor(view: BottomNavigationSecondFragment) : BottomNavigationPresenterImpl<BottomNavigationSecondView>(view), BottomNavigationSecondPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

        drawText("Fragment #2")
    }

    private fun drawText(text: String) {
        mView?.drawText(text)
    }
}
