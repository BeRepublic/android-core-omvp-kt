package com.omvp.components

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class BottomBarItemView : BaseComponentView, Checkable {

    private var mChecked = false

    private lateinit var mTextView: AppCompatTextView
    private lateinit var mImageView: AppCompatCheckBox
    private lateinit var mCounterTextView: AppCompatTextView

    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mOnReCheckedListener: OnReCheckedListener? = null

    private var mCounterBackground: Drawable? = null
    private var mCounterColor: Int = 0
    private var mIconRes: Int = 0

    private var mAnimate: Boolean = false

    private var mCount = 0

    override val layoutId: Int
        get() = R.layout.bottom_bar_item_view

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BottomBarItemView, 0, 0)

        if (mCounterBackground == null) {
            mCounterBackground = ContextCompat.getDrawable(
                    getContext(),
                    typedArray.getResourceId(R.styleable.BottomBarItemView_counter_background, R.drawable.bottom_bar_dot_bg)
            )
        }

        if (mCounterColor <= 0) {
            mCounterColor = typedArray.getResourceId(R.styleable.BottomBarItemView_counter_color, R.color.color_accent)
        }

        if (mIconRes <= 0) {
            mIconRes = typedArray.getResourceId(R.styleable.BottomBarItemView_icon, R.drawable.bottom_navigation_item_home)
        }
    }

    override fun bindViews() {
        mTextView = findViewById(R.id.title)
        mImageView = findViewById(R.id.image)
        mCounterTextView = findViewById(R.id.number)

        isClickable = true
    }

    override fun loadData() {
        mCounterTextView.setBackgroundDrawable(mCounterBackground)
        mCounterTextView.setTextColor(mCounterColor)
        mImageView.setButtonDrawable(mIconRes)
    }

    /**
     * Checkable methods
     */

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        refreshDrawableState()
        setCheckedRecursive(this, checked)
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener!!.onCheckedChanged(this, checked)
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        if (!isChecked) {
            isChecked = !mChecked

        } else {
            if (mOnReCheckedListener != null) {
                mOnReCheckedListener!!.onReChecked(this)
            }
        }
    }

    /**
     * Drawable States
     */

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CheckedStateSet)
        }
        return drawableState
    }

    /**
     * Handle clicks
     */

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return onTouchEvent(ev)
    }

    /**
     * State persistence
     */

    internal class SavedState : View.BaseSavedState, Parcelable {
        var checked: Boolean = false

        constructor(superState: Parcelable) : super(superState)

        private constructor(input : Parcel) : super(input) {
            checked = input.readValue(null) as Boolean
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        // Force our ancestor class to save its state
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)

        ss.checked = isChecked
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState

        super.onRestoreInstanceState(ss.superState)
        isChecked = ss.checked
        requestLayout()
    }

    /**
     * Setters
     */

    fun setText(text: String) {
        mTextView.text = text
        mTextView.visibility = View.VISIBLE
    }

    fun setText(@StringRes textRes: Int) {
        mTextView.setText(textRes)
        mTextView.visibility = View.VISIBLE
    }

    fun setIcon(@DrawableRes iconRes: Int) {
        mImageView.setButtonDrawable(iconRes)
    }

    fun setCounter(count: Int) {
        mCount += count
        mCounterTextView.text = mCount.toString()
        if (mAnimate) {
            mCounterTextView.visibility = View.INVISIBLE
            if (mCount > 0) {
                animateCounter()
            }
        } else {
            mCounterTextView.visibility = if (mCount > 0) View.VISIBLE else View.INVISIBLE
        }
    }

    fun setOnCheckedChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener
    }

    fun setOnReCheckedListener(onReCheckedListener: OnReCheckedListener) {
        mOnReCheckedListener = onReCheckedListener
    }

    fun setCounterBackground(@DrawableRes background: Int) {
        mCounterTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, background))
    }

    fun setCounterBackground(background: Drawable?) {
        mCounterTextView.setBackgroundDrawable(background)
    }

    fun setCounterColor(color: Int) {
        mCounterTextView.setTextColor(color)
    }

    fun setAnimateCounter(animate: Boolean) {
        mAnimate = animate
    }

    private fun setCheckedRecursive(parent: ViewGroup, checked: Boolean) {
        val count = parent.childCount
        for (i in 0 until count) {
            val v = parent.getChildAt(i)
            if (v is Checkable) {
                (v as Checkable).isChecked = checked
            }

            if (v is ViewGroup) {
                setCheckedRecursive(v, checked)
            }
        }
    }

    private fun animateCounter() {
        mCounterTextView.scaleX = 1.8f
        mCounterTextView.scaleY = 1.8f
        mCounterTextView.translationY = -20f

        mCounterTextView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .translationYBy(20f)
                .setDuration(300)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        mCounterTextView.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
    }

    interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param itemView  The button view whose state has changed.
         * @param isChecked The new checked state of buttonView.
         */
        fun onCheckedChanged(itemView: BottomBarItemView, isChecked: Boolean)
    }

    interface OnReCheckedListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param itemView The button view whose state has changed.
         */
        fun onReChecked(itemView: BottomBarItemView)
    }

    companion object {

        private val CheckedStateSet = intArrayOf(android.R.attr.state_checked)
    }
}
