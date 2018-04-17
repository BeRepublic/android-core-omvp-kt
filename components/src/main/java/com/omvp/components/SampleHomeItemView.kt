package com.omvp.components

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

class SampleHomeItemView : BaseComponentView {

    private lateinit var mSampleTextView: AppCompatTextView

    override val layoutId: Int
        get() = R.layout.sample_home_item_layout

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {

    }

    override fun bindViews() {
        mSampleTextView = findViewById(R.id.text)
    }

    override fun loadData() {

    }

    fun setSampleText(text: String) {
        mSampleTextView.text = text
    }

}
