package com.omvp.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import java.util.ArrayList

import timber.log.Timber

/**
 * Input layout custom view
 *
 *
 * Example of use:
 * <com.hobbiespot.mobile.components.InputLayout
 *  android:layout_width="match_parent"
 *  app:hspot_hint="hint"
 *  app:hspot_input_type="password"
 *  android:layout_height="match_parent">
 * </com.hobbiespot.mobile.components.InputLayout>
 */
class InputLayoutView : BaseComponentView {

    private lateinit var mTextInputLayout: TextAdvancedInputLayout
    private lateinit var mTextInputEditText: TextInputEditText
    private lateinit var mHintFixedToLeftText: AppCompatTextView

    private var mText: String = ""
    private var mHint: String = ""
    private var mHintAnimationEnabled: Boolean = false
    private var mHintType: HintType? = null
    private var mHintWidth: Int = 0
    private var mPasswordToggleEnabled: Boolean = false

    private var mInputType: Int = 0
    private var mInputGravity: Int = 0
    private var mImeOptions: Int = 0
    private var mEnabled: Boolean = false
    private var mFocusable: Boolean = false

    private var mNextFocusForward: Int = 0
    private var mNextFocusUp: Int = 0
    private var mNextFocusDown: Int = 0
    private var mNextFocusRight: Int = 0
    private var mNextFocusLeft: Int = 0

    private var mEditable: Boolean = false

    private var mCounterEnabled: Boolean = false
    private var mCounterMaxLength: Int = 0

    private var mMaxLines: Int = 0

    private var mMask: CharSequence? = null
    private var mNotMaskedSymbol: Char = ' '
    private val mMaskedInputFilter: MaskedInputFilter? = null

    private var mSuccessDrawable: Drawable? = null
    private val mSuccessTransparentDrawable: Drawable? = null
    private var mErrorDrawable: Drawable? = null
    private val mErrorTransparentDrawable: Drawable? = null

    override val layoutId: Int
        get() = LAYOUT_ID

    var text: String
        get() = mTextInputEditText.text.toString()
        set(text) {
            if (!TextUtils.isEmpty(text)) {
                mTextInputEditText.setText(text)
            }
        }

    val editTextId: Int
        get() = mTextInputEditText.id

    enum class HintType {
        NONE, FIXED, FIXED_TO_LEFT
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun loadData() {
        setHint(mHint)
        text = mText
        setInputType(mInputType)
        setInputGravity(mInputGravity)
        setImeOptions(mImeOptions)
        setNextFocusForward(mNextFocusForward)
        setNextFocusUp(mNextFocusUp)
        setNextFocusDown(mNextFocusDown)
        setNextFocusRight(mNextFocusRight)
        setNextFocusLeft(mNextFocusLeft)
        setCounterEnabled(mCounterEnabled)
        setEditTextEnabled(mEnabled)
        setEditTextFocusable(mFocusable)
        setPasswordToggleEnabled(mPasswordToggleEnabled)
        setMaxLines(mMaxLines)

        setupFilters()
    }

    override fun loadAttributes(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.InputLayoutView, 0, 0)
        mText = typedArray.getString(R.styleable.InputLayoutView_android_text)
        mHint = typedArray.getString(R.styleable.InputLayoutView_android_hint)
        mInputType = typedArray.getInteger(R.styleable.InputLayoutView_android_inputType, InputType.TYPE_CLASS_TEXT)
        mInputGravity = typedArray.getInteger(R.styleable.InputLayoutView_android_gravity, Gravity.TOP or Gravity.LEFT)
        mSuccessDrawable = ContextCompat.getDrawable(getContext(), R.drawable.input_layout_success)
        mErrorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.input_layout_error)
        if (mSuccessDrawable != null) {
            val width = mSuccessDrawable!!.intrinsicWidth
            val height = mSuccessDrawable!!.intrinsicHeight
            //            mSuccessTransparentDrawable = createDummyDrawable(width, height);
        }
        if (mErrorDrawable != null) {
            val width = mErrorDrawable!!.intrinsicWidth
            val height = mErrorDrawable!!.intrinsicHeight
            //            mErrorTransparentDrawable = createDummyDrawable(width, height);
        }
        mImeOptions = typedArray.getInteger(R.styleable.InputLayoutView_android_imeOptions, 0)
        mNextFocusForward = typedArray.getResourceId(R.styleable.InputLayoutView_android_nextFocusForward, 0)
        mNextFocusUp = typedArray.getResourceId(R.styleable.InputLayoutView_android_nextFocusUp, 0)
        mNextFocusDown = typedArray.getResourceId(R.styleable.InputLayoutView_android_nextFocusDown, 0)
        mNextFocusRight = typedArray.getResourceId(R.styleable.InputLayoutView_android_nextFocusRight, 0)
        mNextFocusLeft = typedArray.getResourceId(R.styleable.InputLayoutView_android_nextFocusLeft, 0)
        mCounterEnabled = typedArray.getBoolean(R.styleable.InputLayoutView_counterEnabled, false)
        mCounterMaxLength = typedArray.getInteger(R.styleable.InputLayoutView_counterMaxLength, -1)
        mHintAnimationEnabled = typedArray.getBoolean(R.styleable.InputLayoutView_hintAnimationEnabled, true)
        mHintType = HintType.values()[typedArray.getInteger(R.styleable.InputLayoutView_hintType, HintType.NONE.ordinal)]
        mHintWidth = typedArray.getDimensionPixelSize(R.styleable.InputLayoutView_hintWidth, -1)
        mEnabled = typedArray.getBoolean(R.styleable.InputLayoutView_android_enabled, true)
        mFocusable = typedArray.getBoolean(R.styleable.InputLayoutView_android_focusable, true)
        mPasswordToggleEnabled = typedArray.getBoolean(R.styleable.InputLayoutView_passwordToggleEnabled, false)
        mEditable = typedArray.getBoolean(R.styleable.InputLayoutView_editable, true)

        mMask = typedArray.getString(R.styleable.InputLayoutView_mask)
        val notMaskedSymbol = typedArray.getString(R.styleable.InputLayoutView_notMaskedSymbol)
        mNotMaskedSymbol = if (TextUtils.isEmpty(notMaskedSymbol)) {
            '#'
        } else {
            notMaskedSymbol!![0]
        }

        mMaxLines = typedArray.getInteger(R.styleable.InputLayoutView_android_maxLines, 1)

        typedArray.recycle()
    }

    private fun createDummyDrawable(width: Int, height: Int): Drawable {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        return BitmapDrawable(resources, bitmap)
    }

    override fun bindViews() {
        mTextInputLayout = findViewById(R.id.input_layout)
        mTextInputEditText = findViewById(R.id.input_text)
        mHintFixedToLeftText = findViewById(R.id.hint_fixed_to_left)
    }

    fun setHint(hint: String) {
        if (!hint.isEmpty()) {
            when (mHintType) {
                InputLayoutView.HintType.NONE -> {
                    mTextInputLayout.hint = hint
                    mTextInputLayout.isHintAnimationEnabled = mHintAnimationEnabled
                }
                InputLayoutView.HintType.FIXED -> {
                    mTextInputLayout.hint = ""
                    mTextInputEditText.hint = hint
                }
                InputLayoutView.HintType.FIXED_TO_LEFT -> {
                    mTextInputLayout.hint = ""
                    mTextInputEditText.hint = ""
                    mTextInputEditText.setPadding(mHintWidth, 0, 0, 0)
                    mHintFixedToLeftText.text = hint
                    mHintFixedToLeftText.width = mHintWidth
                }
            }
        }
    }

    fun clearText() {
        mTextInputEditText.setText("")
    }

    fun setInputType(sourceInputType: Int) {
        var inputType = sourceInputType
        when (inputType) {
            InputType.TYPE_NULL -> {
                mTextInputEditText.isFocusable = false
                mTextInputEditText.isEnabled = false
                mTextInputEditText.isClickable = false
            }
            InputType.TYPE_CLASS_TEXT, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                val drawable = mTextInputEditText.background
                val color = Color.parseColor("#FF0000")
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
            InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT -> (mTextInputLayout.layoutParams as RelativeLayout.LayoutParams).setMargins(0, 0, 0, 0)
        }
        if (!TextUtils.isEmpty(mMask)) {
            inputType = inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }
        mTextInputEditText.inputType = inputType
    }

    fun setInputGravity(inputGravity: Int) {
        mTextInputLayout.gravity = inputGravity
        mTextInputEditText.gravity = inputGravity
        if (mInputGravity != inputGravity) {
            mInputGravity = inputGravity
            invalidate()
        }
    }

    fun setErrorEnabled(enabled: Boolean) {
        mTextInputLayout.isErrorEnabled = enabled
    }

    fun clearError() {
        mTextInputLayout.isErrorEnabled = false
        mTextInputLayout.error = ""
        mTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    fun setSuccess() {
        mTextInputLayout.isErrorEnabled = false
        mTextInputLayout.error = ""
        mTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    fun setError(error: CharSequence) {
        if (!TextUtils.isEmpty(error)) {
            mTextInputLayout.isErrorEnabled = true
            mTextInputLayout.error = error
            mTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    mErrorDrawable, null)
        } else {
            clearError()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setEditTextEnabled(enabled)
    }

    override fun setFocusable(focusable: Boolean) {
        super.setFocusable(focusable)
        setEditTextFocusable(focusable)
    }

    fun setEditTextEnabled(enabled: Boolean) {
        mTextInputEditText.isEnabled = enabled
    }

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        mTextInputEditText.setOnEditorActionListener(listener)
    }

    fun addTextChangedListener(listener: TextWatcher) {
        mTextInputEditText.addTextChangedListener(listener)
    }

    override fun setOnFocusChangeListener(listener: View.OnFocusChangeListener?) {
        if (listener == null) {
            mTextInputEditText.onFocusChangeListener = null
            return
        }

        mTextInputEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus -> listener.onFocusChange(this@InputLayoutView, hasFocus) }
    }

    fun setEditTextFocusable(focusable: Boolean) {
        mTextInputEditText.isFocusable = focusable
        mTextInputEditText.isFocusableInTouchMode = focusable
    }

    fun setEditTextClickListener(listener: View.OnClickListener?) {
        if (listener == null) {
            mTextInputEditText.setOnClickListener(null)
            return
        }

        mTextInputEditText.setOnClickListener { listener.onClick(this@InputLayoutView) }
    }

    fun requestEditTextFocus(): Boolean {
        return mTextInputEditText.requestFocus()
    }

    private fun setPasswordToggleEnabled(passwordToggleEnabled: Boolean) {
        mTextInputLayout.isPasswordVisibilityToggleEnabled = passwordToggleEnabled
    }

    private fun setImeOptions(imeOptions: Int) {
        if (imeOptions != 0) {
            mTextInputEditText.imeOptions = imeOptions
        }
    }

    private fun setNextFocusForward(nextFocusForwardId: Int) {
        if (nextFocusForwardId != 0) {
            mTextInputEditText.nextFocusForwardId = nextFocusForwardId
        }
    }

    private fun setNextFocusUp(nextFocusUpId: Int) {
        if (nextFocusUpId != 0) {
            mTextInputEditText.nextFocusUpId = nextFocusUpId
        }
    }

    private fun setNextFocusDown(nextFocusDownId: Int) {
        if (nextFocusDownId != 0) {
            mTextInputEditText.nextFocusDownId = nextFocusDownId
        }
    }

    private fun setNextFocusRight(nextFocusRightId: Int) {
        if (nextFocusRightId != 0) {
            mTextInputEditText.nextFocusRightId = nextFocusRightId
        }
    }

    private fun setNextFocusLeft(nextFocusLeftId: Int) {
        if (nextFocusLeftId != 0) {
            mTextInputEditText.nextFocusLeftId = nextFocusLeftId
        }
    }

    private fun setCounterEnabled(counterEnabled: Boolean) {
        mTextInputLayout.isCounterEnabled = counterEnabled
    }

    private fun setupFilters() {
        val filterList = ArrayList<InputFilter>()
        if (!mEditable) {
            filterList.add(DisableEditInputFilter())
        } else if (!TextUtils.isEmpty(mMask)) {
            filterList.add(MaskedInputFilter())
            filterList.add(InputFilter.LengthFilter(mMask!!.length))
        } else if (mCounterMaxLength != -1) {
            filterList.add(InputFilter.LengthFilter(mCounterMaxLength))
            mTextInputLayout.counterMaxLength = mCounterMaxLength
        }
        mTextInputEditText.filters = filterList.toTypedArray()
    }

    private fun setMaxLines(maxLines: Int) {
        mTextInputEditText.maxLines = maxLines
    }

    private inner class DisableEditInputFilter : InputFilter {
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            return if (source.isEmpty()) dest.subSequence(dstart, dend) else ""
        }
    }

    private inner class MaskedInputFilter : InputFilter {

        override fun filter(sourceIn: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
            var source = sourceIn

            val newCharToEndOfWord = dstart == dend && dest.length <= dstart
            val newCharBetweenOfWord = dstart == dend && dest.length > dstart
            val newCharEntered = newCharToEndOfWord || newCharBetweenOfWord
            val charRemoved = !newCharToEndOfWord && !newCharBetweenOfWord
            val currentTextLength = dest.length
            val maskLenght = mMask!!.length
            val exceededMaxLength = currentTextLength >= maskLenght
            val nextCharPosition = dstart + 1
            val previousCharPosition = dstart - 1

            Timber.d("%s %d %d %s %d %d - %s %s", source, start, end, dest, dstart, dend, newCharToEndOfWord, newCharBetweenOfWord)

            if (newCharEntered && exceededMaxLength) {
                return ""
            } else if (newCharToEndOfWord) {
                val currentMaskChar = mMask!![dstart]
                if (nextCharPosition < maskLenght) {
                    val nextMaskChar = mMask!![nextCharPosition]
                    if (nextMaskChar != mNotMaskedSymbol) {
                        source = source.toString() + "" + nextMaskChar
                    } else if (currentMaskChar != mNotMaskedSymbol) {
                        source = currentMaskChar + "" + source
                    }
                }
            } else if (charRemoved && previousCharPosition > 0) {
                val currentMaskChar = mMask!![dstart]
                if (currentMaskChar != mNotMaskedSymbol) {
                    mTextInputEditText.setText(dest)
                    mTextInputEditText.setSelection(dend)
                }
                val previousMaskChar = mMask!![previousCharPosition]
                if (previousMaskChar != mNotMaskedSymbol) {
                    mTextInputEditText.setText(dest.subSequence(0, previousCharPosition))
                    mTextInputEditText.setSelection(mTextInputEditText.text.length)
                }
            }

            return source
        }

    }

    companion object {

        private val LAYOUT_ID = R.layout.input_layout
    }

}
