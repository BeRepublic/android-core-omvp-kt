package com.omvp.app.ui.samples.bottomnavigation.presenter


import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationFirstFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationFirstView

import javax.inject.Inject

class BottomNavigationFirstPresenterImpl @Inject
constructor(view: BottomNavigationFirstFragment) : BottomNavigationPresenterImpl<BottomNavigationFirstView>(view),
        BottomNavigationFirstPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()

        drawText("Fragment #1")
    }

    private fun drawText(text: String) {
        mView?.drawText(text)
    }
}
