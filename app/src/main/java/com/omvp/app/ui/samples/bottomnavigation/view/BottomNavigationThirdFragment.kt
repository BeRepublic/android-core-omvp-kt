package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationThirdPresenterImpl

class BottomNavigationThirdFragment : BottomNavigationFragment<BottomNavigationThirdPresenterImpl,
        BottomNavigationThirdFragment.BottomNavigationThirdFragmentCallback>(),
        BottomNavigationThirdView {

//    internal lateinit var mTextView: AppCompatTextView

    interface BottomNavigationThirdFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
//        mTextView.text = text
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
