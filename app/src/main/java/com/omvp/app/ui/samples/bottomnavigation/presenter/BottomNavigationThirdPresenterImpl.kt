package com.omvp.app.ui.samples.bottomnavigation.presenter


import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationThirdFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationThirdView

import javax.inject.Inject

class BottomNavigationThirdPresenterImpl @Inject
constructor(view: BottomNavigationThirdFragment) : BottomNavigationPresenterImpl<BottomNavigationThirdView>(view), BottomNavigationThirdPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

        drawText("Fragment #3")
    }

    private fun drawText(text: String) {
        mView?.drawText(text)
    }
}
