package com.omvp.app.ui.samples.list.adapter

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.omvp.app.model.SampleModel
import com.omvp.components.SampleItemView
import com.raxdenstudios.recycler.RecyclerAdapter

class SampleListAdapter(context: Context, private val mAdapterCallback: AdapterCallback) :
        RecyclerAdapter<SampleModel, SampleListAdapter.SampleListViewHolder>(context, -1) {

    internal var mItemTouchHelper: ItemTouchHelper? = null

    interface AdapterCallback {
        fun sampleItemSelected(position: Int, sharedView: View)

        fun sampleItemDeleteSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleListViewHolder {
        return SampleListViewHolder(SampleItemView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        })
    }

    override fun onBindViewItemHolder(holder: SampleListViewHolder, data: SampleModel, position: Int) {
        holder.bindView(data)

        holder.mItemView.getDragView().setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper?.startDrag(holder)
            }
            false
        }
    }

    /*
       Override {@link #onBindViewHolder(ViewHolder, int, List)} if Adapter can handle efficient partial bind.
    */
    override fun onBindViewHolder(holder: SampleListViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewItemHolder(holder, mData[position], position)

        } else {
            val bundle = payloads[0] as Bundle
            if (bundle.containsKey("sample")) {
                val sampleModel = bundle.getParcelable<SampleModel>("sample")
                holder.bindView(sampleModel)
            }
        }
    }

    /*
    * Add this method to update the adapter via DiffUtil.
    */
    fun onNewData(newData: List<SampleModel>) {
        val diffResult = DiffUtil.calculateDiff(SampleDiffUtilsCallback(newData, mData))
        diffResult.dispatchUpdatesTo(this)
        mData.clear()
        mData.addAll(newData)
    }

    inner class SampleListViewHolder(val mItemView: SampleItemView) :
            RecyclerView.ViewHolder(mItemView),
            View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
            mItemView.setDeleteClickListener(View.OnClickListener { mAdapterCallback.sampleItemDeleteSelected(adapterPosition) })
        }

        fun bindView(data: SampleModel) {
            mItemView.setSampleText(data.title)
            mItemView.setSampleImage(data.imageResId)
        }

        override fun onClick(v: View) {
            mAdapterCallback.sampleItemSelected(adapterPosition, mItemView.getSharedView())
        }
    }
}
