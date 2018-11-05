package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationSecondPresenterImpl
import kotlinx.android.synthetic.main.bottom_navigation_second_fragment.*

class BottomNavigationSecondFragment : BottomNavigationFragment<BottomNavigationSecondPresenterImpl,
        BottomNavigationSecondFragment.BottomNavigationSecondFragmentCallback>(),
        BottomNavigationSecondView {

    interface BottomNavigationSecondFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
        text_view.text = text
    }

    fun onCounterIncrementClicked() {
        mCallback.onIncrementSelected(1)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = BottomNavigationSecondFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
