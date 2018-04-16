package com.omvp.app.ui.samples.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.omvp.app.model.SampleModel
import com.omvp.components.SampleItemView
import com.raxdenstudios.recycler.RecyclerAdapter

class SampleListAdapter(context: Context, private val mAdapterCallback: AdapterCallback) :
        RecyclerAdapter<SampleModel, SampleListAdapter.SampleListViewHolder>(context, -1) {

    interface AdapterCallback {
        fun sampleItemSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleListViewHolder {
        return SampleListViewHolder(SampleItemView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        })
    }

    override fun onBindViewItemHolder(holder: SampleListViewHolder, data: SampleModel, position: Int) {
        holder.bindView(data)
    }

    inner class SampleListViewHolder(private val mItemView: SampleItemView) :
            RecyclerView.ViewHolder(mItemView),
            View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
        }

        fun bindView(data: SampleModel) {
            mItemView.setSampleText(data.title)
        }

        override fun onClick(v: View) {
            mAdapterCallback.sampleItemSelected(adapterPosition)
        }
    }
}
