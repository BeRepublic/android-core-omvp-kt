package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationSecondPresenterImpl

class BottomNavigationSecondFragment : BottomNavigationFragment<BottomNavigationSecondPresenterImpl,
        BottomNavigationSecondFragment.BottomNavigationSecondFragmentCallback>(),
        BottomNavigationSecondView {

//    internal lateinit var mTextView: AppCompatTextView

    interface BottomNavigationSecondFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
//        mTextView.text = text
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
