package com.omvp.app.ui.samples.list.view

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.model.SampleModel
import com.omvp.app.ui.samples.list.adapter.SampleListAdapter
import com.omvp.app.ui.samples.list.presenter.SampleListPresenter
import com.omvp.domain.SampleDomain

import butterknife.BindView

class SampleListFragment : BaseViewFragment<SampleListPresenter, SampleListFragment.FragmentCallback>(), SampleListView {

    @BindView(R.id.recycler_view)
    internal lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.empty_view)
    internal lateinit var mEmptyView: View

    private val mAdapter: SampleListAdapter by lazy {
        SampleListAdapter(activity, mPresenter as SampleListAdapter.AdapterCallback)
    }

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onSampleItemSelected(sampleDomain: SampleDomain)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    override fun drawSampleList(sampleModelList: List<SampleModel>) {
        mAdapter.items = sampleModelList

        mEmptyView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showEmptyView() {
        mEmptyView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    override fun onSampleItemSelected(sampleDomain: SampleDomain) {
        mCallback.onSampleItemSelected(sampleDomain)
    }

    private fun setupViews() {
        mRecyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleListFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
