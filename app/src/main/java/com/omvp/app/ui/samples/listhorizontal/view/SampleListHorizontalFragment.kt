package com.omvp.app.ui.samples.listhorizontal.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.model.SampleModel
import com.omvp.app.ui.samples.listhorizontal.adapter.SampleListAdapter
import com.omvp.app.ui.samples.listhorizontal.presenter.SampleListHorizontalPresenter
import com.omvp.domain.SampleDomain
import kotlinx.android.synthetic.main.sample_list_empty_view.*
import kotlinx.android.synthetic.main.sample_list_horizontal_fragment.*

class SampleListHorizontalFragment : BaseViewFragment<SampleListHorizontalPresenter, SampleListHorizontalFragment.FragmentCallback>(), SampleListHorizontalView {

    private lateinit var mAdapter: SampleListAdapter

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sample_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            mPresenter.onAddSampleItemSelected()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    override fun drawSampleList(sampleModelList: List<Any>) {
        mAdapter.items = sampleModelList

        empty_view.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun showEmptyView() {
        empty_view.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    override fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View?) {
        mCallback.onSampleItemSelected(sampleDomain, sharedView)
    }

    override fun drawRemoveAnimation(position: Int) {
        mAdapter.removeItem(position)
    }

    override fun drawAddAnimation(model: SampleModel) {
        mAdapter.addItem(model)

        Toast.makeText(mContext, "Added " + model.title, Toast.LENGTH_SHORT).show()
    }

    private fun setupViews() {
        mAdapter = SampleListAdapter(context!!,
                mPresenter as SampleListAdapter.AdapterCallback)

        val manager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        /*
            Call LinearLayoutManager.setInitialPrefetchItemCount(N), where N is the number of views visible per inner item.
            For example, if your inner, horizontal lists show a minimum of three and a half item views at a time,
            you can improve performance by calling LinearLayoutManager.setInitialPrefetchItemCount(4).
            Doing so allows RecyclerView to create all relevant views early, while the outer RecyclerView is scrolling,
            which significantly reduces the amount of stuttering during scrolls.
         */
        manager.initialPrefetchItemCount = 3

        recycler_view.let {
            it.layoutManager = manager
            it.setHasFixedSize(false)
            it.adapter = mAdapter
            it.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleListHorizontalFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
