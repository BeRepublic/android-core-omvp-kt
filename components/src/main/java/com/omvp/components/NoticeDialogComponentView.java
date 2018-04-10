package com.omvp.components;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import butterknife.ButterKnife;

public class NoticeDialogComponentView extends BaseComponentView {

    AppCompatTextView mTitleTextView;
    AppCompatTextView mDescriptionTextView;
    AppCompatTextView mAcceptTextView;
    AppCompatTextView mDenyTextView;

    public NoticeDialogComponentView(Context context) {
        super(context);
    }

    public NoticeDialogComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticeDialogComponentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void loadAttributes(Context context, AttributeSet attrs) {

    }

    @Override
    protected void bindViews() {
        mTitleTextView = ButterKnife.findById(this, R.id.title);
        mDescriptionTextView = ButterKnife.findById(this, R.id.description);
        mAcceptTextView = ButterKnife.findById(this, R.id.accept);
        mDenyTextView = ButterKnife.findById(this, R.id.deny);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.notice_dialog_component_view;
    }

    public void setTitleText(String text) {
        mTitleTextView.setText(text);
    }

    public void setDescriptionText(String text) {
        mDescriptionTextView.setText(text);
    }

    public void setAcceptTextButton(String text, OnClickListener onClickListener) {
        mAcceptTextView.setText(text);
        mAcceptTextView.setOnClickListener(onClickListener);
        mAcceptTextView.setVisibility(View.VISIBLE);
    }

    public void setDenyTextButton(String text, OnClickListener onClickListener) {
        mDenyTextView.setText(text);
        mDenyTextView.setOnClickListener(onClickListener);
        mDenyTextView.setVisibility(View.VISIBLE);
    }

}
