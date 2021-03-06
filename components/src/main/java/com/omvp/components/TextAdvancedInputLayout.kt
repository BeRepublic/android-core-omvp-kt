package com.omvp.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber

/**
 * Created by Angel on 09/10/2017.
 */

class TextAdvancedInputLayout : TextInputLayout {

    var mErrorGravity = Gravity.END

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (!enabled) {
            return
        }
        try {
            val errorViewField = TextInputLayout::class.java.getDeclaredField("mErrorView")
            errorViewField.isAccessible = true
            val errorView = errorViewField.get(this) as TextView
            errorView.gravity = mErrorGravity
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = mErrorGravity
            errorView.layoutParams = params

        } catch (e: Exception) {
            Timber.e(e)
        }

    }

}
