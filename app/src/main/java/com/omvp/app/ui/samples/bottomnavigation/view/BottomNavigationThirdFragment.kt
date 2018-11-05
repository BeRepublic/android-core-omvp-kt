package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationThirdPresenterImpl
import kotlinx.android.synthetic.main.bottom_navigation_third_fragment.*

class BottomNavigationThirdFragment : BottomNavigationFragment<BottomNavigationThirdPresenterImpl,
        BottomNavigationThirdFragment.BottomNavigationThirdFragmentCallback>(),
        BottomNavigationThirdView {

    interface BottomNavigationThirdFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
        text_view.text = text
    }

    fun onCounterIncrementClicked() {
        mCallback.onIncrementSelected(2)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = BottomNavigationThirdFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
