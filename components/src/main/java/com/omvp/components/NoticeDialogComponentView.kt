package com.omvp.components

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View

import butterknife.ButterKnife

class NoticeDialogComponentView : BaseComponentView {

    internal lateinit var mTitleTextView: AppCompatTextView
    internal lateinit var mDescriptionTextView: AppCompatTextView
    internal lateinit var mAcceptTextView: AppCompatTextView
    internal lateinit var mDenyTextView: AppCompatTextView

    override val layoutId: Int
        get() = R.layout.notice_dialog_component_view

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {

    }

    override fun bindViews() {
        mTitleTextView = findViewById(R.id.title)
        mDescriptionTextView = findViewById(R.id.description)
        mAcceptTextView = findViewById(R.id.accept)
        mDenyTextView = findViewById(R.id.deny)
    }

    override fun loadData() {

    }

    fun setTitleText(text: String) {
        mTitleTextView.text = text
    }

    fun setDescriptionText(text: String) {
        mDescriptionTextView.text = text
    }

    fun setAcceptTextButton(text: String, onClickListener: View.OnClickListener) {
        mAcceptTextView.text = text
        mAcceptTextView.setOnClickListener(onClickListener)
        mAcceptTextView.visibility = View.VISIBLE
    }

    fun setDenyTextButton(text: String, onClickListener: View.OnClickListener) {
        mDenyTextView.text = text
        mDenyTextView.setOnClickListener(onClickListener)
        mDenyTextView.visibility = View.VISIBLE
    }
}
