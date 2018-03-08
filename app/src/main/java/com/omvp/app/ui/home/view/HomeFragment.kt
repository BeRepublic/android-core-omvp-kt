package com.omvp.app.ui.home.view

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.home.presenter.HomePresenter

class HomeFragment : BaseViewFragment<HomePresenter, HomeFragment.FragmentCallback>(), HomeView {

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onSampleViewSelected()

        fun onSampleListSelected()

        fun onSamplePagerSelected()

        fun onSampleMultipleSelected()
    }

    @OnClick(R.id.button_view)
    fun onSampleViewClicked(view: View) {
        mCallback.onSampleViewSelected()
    }

    @OnClick(R.id.button_list)
    fun onSampleListClicked(view: View) {
        mCallback.onSampleListSelected()
    }

    @OnClick(R.id.button_pager)
    fun onSamplePagerClicked(view: View) {
        mCallback.onSamplePagerSelected()
    }

    @OnClick(R.id.button_multiple)
    fun onSampleMultipleClicked(view: View) {
        mCallback.onSampleMultipleSelected()
    }

    companion object {

        fun newInstance(bundle: Bundle?) = HomeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
