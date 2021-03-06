package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationFirstPresenterImpl
import kotlinx.android.synthetic.main.bottom_navigation_first_fragment.*

class BottomNavigationFirstFragment : BottomNavigationFragment<BottomNavigationFirstPresenterImpl, BottomNavigationFirstFragment.BottomNavigationFirstFragmentCallback>(), BottomNavigationFirstView {
    
    interface BottomNavigationFirstFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
        text_view.text = text
    }

    fun onCounterIncrementClicked() {
        mCallback.onIncrementSelected(0)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = BottomNavigationFirstFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
