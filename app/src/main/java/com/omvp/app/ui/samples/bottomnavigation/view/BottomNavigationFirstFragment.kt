package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationFirstPresenterImpl

class BottomNavigationFirstFragment : BottomNavigationFragment<BottomNavigationFirstPresenterImpl, BottomNavigationFirstFragment.BottomNavigationFirstFragmentCallback>(), BottomNavigationFirstView {

//    internal lateinit var mTextView: AppCompatTextView

    interface BottomNavigationFirstFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
//        mTextView.text = text
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
