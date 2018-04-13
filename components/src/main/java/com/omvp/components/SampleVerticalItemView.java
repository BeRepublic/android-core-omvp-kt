package com.omvp.components;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

public class SampleVerticalItemView extends BaseComponentView {

    private AppCompatTextView mSampleTextView;
    private AppCompatImageView mSampleImageView;
//    private AppCompatImageButton mDeleteButtonView;

    public SampleVerticalItemView(Context context) {
        super(context);
    }

    public SampleVerticalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SampleVerticalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void loadAttributes(Context context, AttributeSet attrs) {

    }

    @Override
    protected void bindViews() {
        mSampleTextView = findViewById(R.id.text);
        mSampleImageView = findViewById(R.id.image);
//        mDeleteButtonView = findViewById(R.id.delete_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSampleImageView.setTransitionName("item");
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.sample_vertical_item_layout;
    }

    public void setSampleText(String text) {
        mSampleTextView.setText(text);
    }

    public void setSampleImage(@DrawableRes Integer imageResId) {
        mSampleImageView.setImageResource(imageResId);
    }

    public void setDeleteClickListener(OnClickListener clickListener) {
//        mDeleteButtonView.setOnClickListener(clickListener);
    }

    public View getSharedView() {
        return mSampleImageView;
    }
}
