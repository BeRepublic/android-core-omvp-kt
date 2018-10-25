package com.omvp.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

class BottomBarView : BaseComponentView, BottomBarItemView.OnCheckedChangeListener {

    private lateinit var mRootView: LinearLayout

    private var mIconsArray: IntArray? = null

    private var mViewPager: ViewPager? = null

    private var mItemsArray: SparseArray<BottomBarItemView> = SparseArray()

    override val layoutId: Int
        get() = R.layout.bottom_bar_view

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {

    }

    override fun bindViews() {
        mRootView = findViewById(R.id.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = resources.getDimensionPixelSize(R.dimen.elevation).toFloat()
            outlineProvider = ViewOutlineProvider.BOUNDS
        }
    }

    override fun loadData() {

    }

    fun setViewPager(viewPager: ViewPager, iconsArray: IntArray) {
        mViewPager = viewPager
        mIconsArray = iconsArray
        addItems()
    }

    private fun addItems() {
        val params = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.weight = 1f

        val numItems = mViewPager?.adapter?.count ?: 0

        for (i in 0 until numItems) {
            val itemView = BottomBarItemView(context).also {
                it.setIcon(getIcon(i))
                it.layoutParams = params
                it.setAnimateCounter(true)
                it.tag = i
                it.isChecked = i == 0
                it.setOnCheckedChangeListener(this)
                it.setCounterColor(ContextCompat.getColor(context, android.R.color.white))
                it.setCounterBackground(R.drawable.bottom_bar_dot_bg)
            }

            mRootView.addView(itemView)
            mItemsArray.put(i, itemView)
        }
    }

    private fun getIcon(position: Int): Int {
        return if (mIconsArray == null || position >= mIconsArray!!.size) {
            R.drawable.bottom_navigation_item_home
        } else mIconsArray!![position]

    }

    override fun onCheckedChanged(itemView: BottomBarItemView, isChecked: Boolean) {
        if (isChecked) {
            for (i in 0 until mItemsArray.size()) {
                if (itemView.tag as Int != i) {
                    val item = mItemsArray.valueAt(i)
                    item.isChecked = false
                }
            }

            mViewPager?.setCurrentItem(itemView.tag as Int, false)
        }
    }

    fun incrementCounterAt(position: Int) {
        mItemsArray.valueAt(position).setCounter(1)
    }
}
