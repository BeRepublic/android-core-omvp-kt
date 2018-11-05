package com.omvp.app.ui.samples.list.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.model.SampleModel
import com.omvp.app.ui.samples.list.adapter.SampleListAdapter
import com.omvp.app.ui.samples.list.presenter.SampleListPresenter
import com.omvp.app.util.RecyclerDragHelper
import com.omvp.domain.SampleDomain
import kotlinx.android.synthetic.main.sample_list_empty_view.*
import kotlinx.android.synthetic.main.sample_list_fragment.*

class SampleListFragment : BaseViewFragment<SampleListPresenter, SampleListFragment.FragmentCallback>(), SampleListView {

    private val mAdapter: SampleListAdapter by lazy {
        SampleListAdapter(context!!, mPresenter as SampleListAdapter.AdapterCallback)
    }

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View)
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

    override fun drawSampleList(sampleModelList: List<SampleModel>) {
        mAdapter.items = sampleModelList

        empty_view.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun showEmptyView() {
        empty_view.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    override fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View) {
        mCallback.onSampleItemSelected(sampleDomain, sharedView)
    }

    override fun drawRemoveAnimation(position: Int) {
        mAdapter.removeItem(position)
    }

    override fun drawAddAnimation(model: SampleModel) {
        mAdapter.addItem(model)

        Toast.makeText(mContext, "Added " + model.title, Toast.LENGTH_SHORT).show()
    }

    override fun drawViewMoved(oldPosition: Int, newPosition: Int) {
        mAdapter.moveItem(oldPosition, newPosition)
    }

    override fun drawViewSwiped(position: Int) {
        mAdapter.removeItem(position)
    }

    override fun drawUpdatedItems(updatedList: List<SampleModel>) {
        mAdapter.onNewData(updatedList)
    }

    override fun hideProgress() {
        super.hideProgress()
        swipe_refresh_layout.isRefreshing = false
    }

    private fun setupViews() {
        recycler_view.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))

            val dragHelper = RecyclerDragHelper(mPresenter as RecyclerDragHelper.ActionCompletionContract)
            val touchHelper = ItemTouchHelper(dragHelper)
            mAdapter.mItemTouchHelper = touchHelper
            touchHelper.attachToRecyclerView(recycler_view)
        }
        swipe_refresh_layout.setOnRefreshListener(mPresenter as SwipeRefreshLayout.OnRefreshListener)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleListFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
