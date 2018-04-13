package com.omvp.app.ui.samples.listhorizontal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.omvp.app.model.SampleModel;
import com.omvp.components.SampleVerticalItemView;
import com.raxdenstudios.recycler.RecyclerAdapter;

/**
 * Created by stefano on 26/03/2018.
 */

public class SampleListHorizontalAdapter extends RecyclerAdapter<SampleModel, SampleListHorizontalAdapter.SampleListHorizontalItemViewHolder> {

    public interface AdapterCallback {
        void sampleItemSelected(int position, View sharedView);
    }

    private AdapterCallback mAdapterCallback;

    public SampleListHorizontalAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public SampleListHorizontalItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        SampleVerticalItemView sampleItemView = new SampleVerticalItemView(parent.getContext());
        sampleItemView.setLayoutParams(params);
        return new SampleListHorizontalItemViewHolder(sampleItemView);
    }

    @Override
    public void onBindViewItemHolder(SampleListHorizontalItemViewHolder holder, SampleModel data, int position) {
        holder.bindView(data);
    }

    public void setAdapterCallback(AdapterCallback adapterCallback) {
        mAdapterCallback = adapterCallback;
    }

    class SampleListHorizontalItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SampleVerticalItemView mItemView;

        public SampleListHorizontalItemViewHolder(SampleVerticalItemView itemView) {
            super(itemView);

            mItemView = itemView;
            mItemView.setOnClickListener(this);
        }

        public void bindView(SampleModel data) {
            mItemView.setSampleImage(data.getImageResId());
            mItemView.setSampleText(data.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (mAdapterCallback != null) {
                mAdapterCallback.sampleItemSelected(getAdapterPosition(), mItemView.getSharedView());
            }
        }
    }
}
