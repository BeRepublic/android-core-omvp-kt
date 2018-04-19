package com.omvp.components

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.TargetApi
import android.content.Context
import android.database.DataSetObserver
import android.os.Build
import android.support.annotation.AnimatorRes
import android.support.annotation.DrawableRes
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Interpolator
import android.widget.LinearLayout

class PagerIndicator : LinearLayout {
    private var mViewpager: ViewPager? = null
    private var mIndicatorMargin = -1
    private var mIndicatorWidth = -1
    private var mIndicatorHeight = -1
    private var mAnimatorResId = R.animator.scale_with_alpha
    private var mAnimatorReverseResId = 0
    private var mIndicatorBackgroundResId = R.drawable.white_radius
    private var mIndicatorUnselectedBackgroundResId = R.drawable.white_radius
    private lateinit var mAnimatorOut: Animator
    private lateinit var mAnimatorIn: Animator
    private lateinit var mImmediateAnimatorOut: Animator
    private lateinit var mImmediateAnimatorIn: Animator

    private var mLastPosition = -1

    private val mInternalPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {

            if (mViewpager!!.adapter?.count ?: 0 <= 0) {
                return
            }

            if (mAnimatorIn.isRunning) {
                mAnimatorIn.end()
                mAnimatorIn.cancel()
            }

            if (mAnimatorOut.isRunning) {
                mAnimatorOut.end()
                mAnimatorOut.cancel()
            }

            val currentIndicator = getChildAt(mLastPosition)
            if (mLastPosition >= 0 && currentIndicator != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId)
                mAnimatorIn.setTarget(currentIndicator)
                mAnimatorIn.start()
            }

            val selectedIndicator = getChildAt(position)
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId)
                mAnimatorOut.setTarget(selectedIndicator)
                mAnimatorOut.start()
            }
            mLastPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    // No change
    val dataSetObserver: DataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            if (mViewpager == null) {
                return
            }

            val newCount = mViewpager!!.adapter!!.count
            val currentCount = childCount

            if (newCount == currentCount) {
                return
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager!!.currentItem
            } else {
                mLastPosition = -1
            }

            createIndicators()
        }
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        handleTypedArray(context, attrs)
        checkIndicatorConfig(context)
    }

    private fun handleTypedArray(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator)
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_pi_width, -1)
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_pi_height, -1)
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_pi_margin, -1)

        mAnimatorResId = typedArray.getResourceId(R.styleable.PagerIndicator_pi_animator,
                R.animator.scale_with_alpha)
        mAnimatorReverseResId = typedArray.getResourceId(R.styleable.PagerIndicator_pi_animator_reverse, 0)
        mIndicatorBackgroundResId = typedArray.getResourceId(R.styleable.PagerIndicator_pi_drawable,
                R.drawable.white_radius)
        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(R.styleable.PagerIndicator_pi_drawable_unselected,
                mIndicatorBackgroundResId)

        val orientation = typedArray.getInt(R.styleable.PagerIndicator_pi_orientation, -1)
        setOrientation(if (orientation == LinearLayout.VERTICAL) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL)

        val gravity = typedArray.getInt(R.styleable.PagerIndicator_pi_gravity, -1)
        setGravity(if (gravity >= 0) gravity else Gravity.CENTER)

        typedArray.recycle()
    }

    @JvmOverloads
    fun configureIndicator(indicatorWidth: Int, indicatorHeight: Int, indicatorMargin: Int,
                           @AnimatorRes animatorId: Int = R.animator.scale_with_alpha, @AnimatorRes animatorReverseId: Int = 0,
                           @DrawableRes indicatorBackgroundId: Int = R.drawable.white_radius,
                           @DrawableRes indicatorUnselectedBackgroundId: Int = R.drawable.white_radius) {

        mIndicatorWidth = indicatorWidth
        mIndicatorHeight = indicatorHeight
        mIndicatorMargin = indicatorMargin

        mAnimatorResId = animatorId
        mAnimatorReverseResId = animatorReverseId
        mIndicatorBackgroundResId = indicatorBackgroundId
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId

        checkIndicatorConfig(context)
    }

    private fun checkIndicatorConfig(context: Context) {
        mIndicatorWidth = if (mIndicatorWidth < 0) dip2px(DEFAULT_INDICATOR_WIDTH.toFloat()) else mIndicatorWidth
        mIndicatorHeight = if (mIndicatorHeight < 0) dip2px(DEFAULT_INDICATOR_WIDTH.toFloat()) else mIndicatorHeight
        mIndicatorMargin = if (mIndicatorMargin < 0) dip2px(DEFAULT_INDICATOR_WIDTH.toFloat()) else mIndicatorMargin

        mAnimatorResId = if (mAnimatorResId == 0) R.animator.scale_with_alpha else mAnimatorResId

        mAnimatorOut = createAnimatorOut(context)
        mImmediateAnimatorOut = createAnimatorOut(context)
        mImmediateAnimatorOut.duration = 0

        mAnimatorIn = createAnimatorIn(context)
        mImmediateAnimatorIn = createAnimatorIn(context)
        mImmediateAnimatorIn.duration = 0

        mIndicatorBackgroundResId = if (mIndicatorBackgroundResId == 0) {
            R.drawable.white_radius
        } else {
            mIndicatorBackgroundResId
        }

        mIndicatorUnselectedBackgroundResId = if (mIndicatorUnselectedBackgroundResId == 0) {
            mIndicatorBackgroundResId
        } else {
            mIndicatorUnselectedBackgroundResId
        }
    }

    private fun createAnimatorOut(context: Context): Animator {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId)
    }

    private fun createAnimatorIn(context: Context): Animator {
        val animatorIn: Animator
        if (mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId)
            animatorIn.interpolator = ReverseInterpolator()
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId)
        }
        return animatorIn
    }

    fun setViewPager(viewPager: ViewPager) {
        mViewpager = viewPager
        if (mViewpager!!.adapter != null) {
            mLastPosition = -1
            createIndicators()
            mViewpager!!.removeOnPageChangeListener(mInternalPageChangeListener)
            mViewpager!!.addOnPageChangeListener(mInternalPageChangeListener)
            mInternalPageChangeListener.onPageSelected(mViewpager!!.currentItem)
        }
    }


    @Deprecated("User ViewPager addOnPageChangeListener")
    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        if (mViewpager == null) {
            throw NullPointerException("can not find Viewpager , setViewPager first")
        }
        mViewpager?.removeOnPageChangeListener(onPageChangeListener)
        mViewpager?.addOnPageChangeListener(onPageChangeListener)
    }

    private fun createIndicators() {
        removeAllViews()
        val count = mViewpager?.adapter?.count ?: 0
        if (count <= 0) {
            return
        }
        val currentItem = mViewpager?.currentItem
        val orientation = orientation

        for (i in 0 until count) {
            if (currentItem == i) {
                addIndicator(orientation, mIndicatorBackgroundResId, mImmediateAnimatorOut)
            } else {
                addIndicator(orientation, mIndicatorUnselectedBackgroundResId, mImmediateAnimatorIn)
            }
        }
    }

    private fun addIndicator(orientation: Int, @DrawableRes backgroundDrawableId: Int,
                             animator: Animator) {
        if (animator.isRunning) {
            animator.end()
            animator.cancel()
        }

        val indicator = View(context)
        indicator.setBackgroundResource(backgroundDrawableId)
        addView(indicator, mIndicatorWidth, mIndicatorHeight)
        val lp = indicator.layoutParams as LinearLayout.LayoutParams

        if (orientation == LinearLayout.HORIZONTAL) {
            lp.leftMargin = mIndicatorMargin
            lp.rightMargin = mIndicatorMargin
        } else {
            lp.topMargin = mIndicatorMargin
            lp.bottomMargin = mIndicatorMargin
        }

        indicator.layoutParams = lp

        animator.setTarget(indicator)
        animator.start()
    }

    private inner class ReverseInterpolator : Interpolator {
        override fun getInterpolation(value: Float): Float {
            return Math.abs(1.0f - value)
        }
    }

    private fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    companion object {

        private const val DEFAULT_INDICATOR_WIDTH = 5
    }
}
/**
 * Create and configure Indicator in Java code.
 */
