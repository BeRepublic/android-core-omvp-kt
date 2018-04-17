package com.omvp.app.ui.samples.home.view

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.home.adapter.HomeListAdapter
import com.omvp.app.ui.samples.home.presenter.HomePresenter
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl

import butterknife.BindView

import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.AUTH_PHONE
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.BOTTOM_NAV
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.HORIZONTAL_LIST
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.INPUT
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.LIST
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.LOCALE
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.LOCATION
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.MULTIPLE_FRAGMENTS
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.NOTICE_DIALOG
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.PAGER
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.PICTURE
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.SOCIAL
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.VIBRATION
import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl.Companion.VIEW

class HomeFragment : BaseViewFragment<HomePresenter, HomeFragment.FragmentCallback>(), HomeView {

    @BindView(R.id.recyclerview)
    internal lateinit var mRecyclerView: RecyclerView

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
        mRecyclerView.apply {
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {
        fun newInstance(bundle: Bundle?) = HomeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
