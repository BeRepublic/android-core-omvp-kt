package com.omvp.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

class SampleItemView : BaseComponentView {

    private lateinit var mSampleTextView: AppCompatTextView
    private lateinit var mSampleImageView: AppCompatImageView
    private lateinit var mDeleteButtonView: AppCompatImageButton
    private lateinit var mDragImageView: AppCompatImageView

    override val layoutId: Int
        get() = R.layout.sample_item_layout

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {

    }

    override fun bindViews() {
        mSampleTextView = findViewById(R.id.text)
        mSampleImageView = findViewById(R.id.image)
        mDeleteButtonView = findViewById(R.id.delete_button)
        mDragImageView = findViewById(R.id.drag_image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSampleImageView.transitionName = "item"
        }
    }

    override fun loadData() {

    }

    fun setSampleText(text: String) {
        mSampleTextView.text = text
    }

    fun setSampleImage(@DrawableRes imageResId: Int) {
        mSampleImageView.setImageResource(imageResId)
    }

    fun setDeleteClickListener(clickListener: View.OnClickListener) {
        mDeleteButtonView.setOnClickListener(clickListener)
    }

    fun getSharedView(): View {
        return mSampleImageView
    }

    fun getDragView(): View {
        return mDragImageView
    }
}
