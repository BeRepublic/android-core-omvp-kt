package com.omvp.components

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View

class SampleVerticalItemView : BaseComponentView {

    private lateinit var mSampleTextView: AppCompatTextView
    private lateinit var mSampleImageView: AppCompatImageView

    override val layoutId: Int
        get() = R.layout.sample_vertical_item_layout

    val sharedView: View?
        get() = mSampleImageView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {

    }

    override fun bindViews() {
        mSampleTextView = findViewById(R.id.text)
        mSampleImageView = findViewById(R.id.image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSampleImageView.transitionName = "item"
        }
    }

    override fun loadData() {

    }

    fun setSampleText(text: String) {
        mSampleTextView.text = text
    }

    fun setSampleImage(@DrawableRes imageResId: Int?) {
        mSampleImageView.setImageResource(imageResId!!)
    }
}
