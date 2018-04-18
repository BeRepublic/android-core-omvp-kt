package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.omvp.app.R
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationFirstPresenterImpl

class BottomNavigationFirstFragment : BottomNavigationFragment<BottomNavigationFirstPresenterImpl, BottomNavigationFirstFragment.BottomNavigationFirstFragmentCallback>(), BottomNavigationFirstView {

    @BindView(R.id.text)
    internal lateinit var mTextView: AppCompatTextView

    interface BottomNavigationFirstFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
        mTextView.text = text
    }

    @OnClick(R.id.counter_button)
    fun onCounterIncrementClicked(view: View) {
        mCallback.onIncrementSelected(0)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = BottomNavigationFirstFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
