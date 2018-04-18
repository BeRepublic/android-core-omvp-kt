package com.omvp.app.ui.samples.bottomnavigation.view


import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.omvp.app.R
import com.omvp.app.ui.samples.bottomnavigation.presenter.BottomNavigationThirdPresenterImpl

class BottomNavigationThirdFragment : BottomNavigationFragment<BottomNavigationThirdPresenterImpl,
        BottomNavigationThirdFragment.BottomNavigationThirdFragmentCallback>(),
        BottomNavigationThirdView {

    @BindView(R.id.text)
    internal lateinit var mTextView: AppCompatTextView

    interface BottomNavigationThirdFragmentCallback : BottomNavigationFragment.BottomNavigationFragmentCallback {
        fun onIncrementSelected(position: Int)
    }

    override fun drawText(text: String) {
        mTextView.text = text
    }

    @OnClick(R.id.counter_button)
    fun onCounterIncrementClicked(view: View) {
        mCallback.onIncrementSelected(2)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = BottomNavigationThirdFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
