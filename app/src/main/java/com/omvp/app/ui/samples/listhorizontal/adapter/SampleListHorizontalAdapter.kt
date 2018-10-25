package com.omvp.app.ui.samples.listhorizontal.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.omvp.app.model.SampleModel
import com.omvp.components.SampleVerticalItemView
import com.raxdenstudios.recycler.RecyclerAdapter

/**
 * Created by stefano on 26/03/2018.
 */

class SampleListHorizontalAdapter(context: Context) : RecyclerAdapter<SampleModel, SampleListHorizontalAdapter.SampleListHorizontalItemViewHolder>(context, -1) {

    private var mAdapterCallback: AdapterCallback? = null

    interface AdapterCallback {
        fun sampleItemSelected(position: Int, sharedView: View?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleListHorizontalItemViewHolder {
        val sampleItemView = SampleVerticalItemView(parent.context).apply {
            layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return SampleListHorizontalItemViewHolder(sampleItemView)
    }

    override fun onBindViewItemHolder(holder: SampleListHorizontalItemViewHolder, data: SampleModel, position: Int) {
        holder.bindView(data)
    }

    fun setAdapterCallback(adapterCallback: AdapterCallback) {
        mAdapterCallback = adapterCallback
    }

    inner class SampleListHorizontalItemViewHolder(private val mItemView: SampleVerticalItemView) :
            RecyclerView.ViewHolder(mItemView), View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
        }

        fun bindView(data: SampleModel) {
            mItemView.setSampleImage(data.imageResId)
            mItemView.setSampleText(data.title)
        }

        override fun onClick(v: View) {
            mAdapterCallback?.sampleItemSelected(adapterPosition, mItemView.sharedView)
        }
    }
}
