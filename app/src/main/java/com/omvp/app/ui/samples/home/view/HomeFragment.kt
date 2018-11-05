package com.omvp.app.ui.samples.home.view

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.home.adapter.HomeListAdapter
import com.omvp.app.ui.samples.home.presenter.HomePresenter
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseViewFragment<HomePresenter, HomeFragment.FragmentCallback>(), HomeView {

    private lateinit var mAdapter: HomeListAdapter

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onSampleViewSelected()
        fun onSampleListSelected()
        fun onSamplePagerSelected()
        fun onSampleMultipleSelected()
        fun onSampleLocationSelected()
        fun onSampleTakePictureSelected()
        fun onSampleLocaleSelected()
        fun onSampleHorizontalListClicked()
        fun onVibrationSelected()
        fun onInputViewSelected()
        fun onSocialViewSelected()
        fun onNoticeDialogViewSelected()
        fun onBottomNavigationViewSelected()
        fun onAuthPhoneViewSelected()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupViews()
    }

    override fun itemSelected(item: HomePresenterImpl.SampleItemModel) {
        when (item.type) {
            HomePresenterImpl.VIEW -> mCallback.onSampleViewSelected()
            HomePresenterImpl.LIST -> mCallback.onSampleListSelected()
            HomePresenterImpl.HORIZONTAL_LIST -> mCallback.onSampleHorizontalListClicked()
            HomePresenterImpl.PAGER -> mCallback.onSamplePagerSelected()
            HomePresenterImpl.MULTIPLE_FRAGMENTS -> mCallback.onSampleMultipleSelected()
            HomePresenterImpl.LOCATION -> mCallback.onSampleLocationSelected()
            HomePresenterImpl.PICTURE -> mCallback.onSampleTakePictureSelected()
            HomePresenterImpl.LOCALE -> mCallback.onSampleLocaleSelected()
            HomePresenterImpl.VIBRATION -> mCallback.onVibrationSelected()
            HomePresenterImpl.INPUT -> mCallback.onInputViewSelected()
            HomePresenterImpl.SOCIAL -> mCallback.onSocialViewSelected()
            HomePresenterImpl.NOTICE_DIALOG -> mCallback.onNoticeDialogViewSelected()
            HomePresenterImpl.BOTTOM_NAV -> mCallback.onBottomNavigationViewSelected()
            HomePresenterImpl.AUTH_PHONE -> mCallback.onAuthPhoneViewSelected()
        }
    }

    override fun drawItems(itemList: List<HomePresenterImpl.SampleItemModel>) {
        mAdapter.items = itemList
    }

    private fun setupViews() {
        mAdapter = HomeListAdapter(mContext, mPresenter as HomeListAdapter.AdapterCallback)
        recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {
        fun newInstance(bundle: Bundle?) = HomeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
