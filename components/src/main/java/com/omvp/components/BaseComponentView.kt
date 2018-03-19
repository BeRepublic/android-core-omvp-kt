package com.omvp.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class BaseComponentView : FrameLayout {

    protected abstract val layoutId: Int

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (!isInEditMode) {
            loadView(context)
            loadAttributes(context, attrs)
            bindViews()
            loadData()
        }
    }

    private fun loadView(context: Context) {
        View.inflate(context, layoutId, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) {
            loadView(context)
        }
    }

    protected abstract fun loadAttributes(context: Context, attrs: AttributeSet?)

    protected abstract fun bindViews()

    protected abstract fun loadData()

}
