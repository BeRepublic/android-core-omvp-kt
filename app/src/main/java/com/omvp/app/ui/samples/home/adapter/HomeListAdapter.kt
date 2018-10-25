package com.omvp.app.ui.samples.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.omvp.app.ui.samples.home.presenter.HomePresenterImpl
import com.omvp.components.SampleHomeItemView
import com.raxdenstudios.recycler.RecyclerAdapter

class HomeListAdapter(context: Context, private val mAdapterCallback: AdapterCallback) :
        RecyclerAdapter<HomePresenterImpl.SampleItemModel, HomeListAdapter.HomeListViewHolder>(context, -1) {

    interface AdapterCallback {
        fun itemSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val sampleItemView = SampleHomeItemView(parent.context)
        sampleItemView.layoutParams = params
        return HomeListViewHolder(sampleItemView)
    }

    override fun onBindViewItemHolder(holder: HomeListViewHolder, data: HomePresenterImpl.SampleItemModel, position: Int) {
        holder.bindView(data)
    }

    inner class HomeListViewHolder(private val mItemView: SampleHomeItemView) : RecyclerView.ViewHolder(mItemView),
            View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
        }

        fun bindView(data: HomePresenterImpl.SampleItemModel) {
            mItemView.setSampleText(data.title)
        }

        override fun onClick(v: View) {
            mAdapterCallback.itemSelected(adapterPosition)
        }
    }
}
