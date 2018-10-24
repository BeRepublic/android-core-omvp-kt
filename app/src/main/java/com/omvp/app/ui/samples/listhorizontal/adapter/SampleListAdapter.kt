package com.omvp.app.ui.samples.listhorizontal.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omvp.app.R
import com.omvp.app.model.SampleModel
import com.omvp.components.SampleItemView
import com.raxdenstudios.recycler.RecyclerAdapter

class SampleListAdapter(context: Context, private val mAdapterCallback: AdapterCallback) : RecyclerAdapter<Any, RecyclerView.ViewHolder>(context, -1) {

    /*
        Setting a single view pool for all the inner RecyclerViews. This can be useful if you have
        multiple RecyclerViews with adapters that use the same view types. Now as all the inner
        RecyclerViews have the same view pool, it can use each other’s scraped views.
        Which gives much lesser view creation and better scroll performance.
    */
    private val mRecycledViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    interface AdapterCallback {
        fun sampleItemSelected(position: Int, sharedView: View?)

        fun sampleHorizontalItemSelected(position: Int, horizontalListPosition: Int, sharedView: View?)

        fun sampleItemDeleteSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return when (viewType) {
            HEADER, NORMAL, FOOTER -> {
                val sampleItemView = SampleItemView(parent.context).apply {
                    layoutParams = params
                }
                SampleListViewHolder(sampleItemView)
            }
            HORIZONTAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_list_horizontal_item_layout, parent, false).apply {
                    layoutParams = params
                }
                SampleListHorizontalViewHolder(view)
            }
            else -> {
                val sampleItemView = SampleItemView(parent.context).apply {
                    layoutParams = params
                }
                SampleListViewHolder(sampleItemView)
            }
        }
    }

    override fun onBindViewItemHolder(holder: RecyclerView.ViewHolder, data: Any, position: Int) {
        when (getItemViewType(position)) {
            HORIZONTAL -> (holder as SampleListHorizontalViewHolder).bindView(data)
            NORMAL -> (holder as SampleListViewHolder).bindView(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is SampleModel) {
            NORMAL
        } else {
            HORIZONTAL
        }
    }

    internal inner class SampleListViewHolder(private val mItemView: SampleItemView) : RecyclerView.ViewHolder(mItemView), View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
            mItemView.setDeleteClickListener(View.OnClickListener { mAdapterCallback.sampleItemDeleteSelected(adapterPosition) })
        }

        fun bindView(data: Any) {
            if (data is SampleModel) {
                val (title, _, _, imageResId) = data
                mItemView.setSampleText(title)
                mItemView.setSampleImage(imageResId)
            }
        }

        override fun onClick(v: View) {
            mAdapterCallback.sampleItemSelected(adapterPosition, mItemView.getSharedView())
        }
    }

    internal inner class SampleListHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mRecyclerView: RecyclerView = itemView as RecyclerView
        private val mAdapter: SampleListHorizontalAdapter = SampleListHorizontalAdapter(itemView.context)

        init {

            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)
            mRecyclerView.setHasFixedSize(true)

            // Setting shared recyclerview pool
            mRecyclerView.setRecycledViewPool(mRecycledViewPool)

            mAdapter.setAdapterCallback(object : SampleListHorizontalAdapter.AdapterCallback {
                override fun sampleItemSelected(position: Int, sharedView: View?) {
                    mAdapterCallback.sampleHorizontalItemSelected(adapterPosition, position, sharedView)
                }
            })
        }

        fun bindView(data: Any) {
            if (data is List<*>) {
                mAdapter.items = data as MutableList<SampleModel>?
            }
        }
    }

    companion object {

        private const val HEADER = 0
        private const val NORMAL = 1
        private const val HORIZONTAL = 2
        private const val FOOTER = 3
    }
}
