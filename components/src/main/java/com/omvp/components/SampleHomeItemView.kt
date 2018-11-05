package com.omvp.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

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
        mSampleTextView = findViewById(R.id.text_view)
    }

    override fun loadData() {

    }

    fun setSampleText(text: String) {
        mSampleTextView.text = text
    }

}
