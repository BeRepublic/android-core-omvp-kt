package com.omvp.app.base.mvp

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.omvp.app.R
import com.omvp.app.base.BaseActivity
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.dialog.notice.view.NoticeDialogFragment

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseFragmentActivity : BaseActivity(),
        NoticeDialogFragment.FragmentCallback,
        BaseViewFragmentCallback {

    private var mProgressContainer: View? = null
    private var mProgressTextView: AppCompatTextView? = null

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onContentViewCreated(view, savedInstanceState)

        mProgressContainer = findViewById(R.id.progress)
        mProgressTextView = findViewById(R.id.progress_label)
    }

    // =============== BaseViewFragmentCallback ====================================================

    override fun showProgress(progress: Float, message: String?) {
        mProgressContainer?.let { mAnimationHelper.fadeIn(it) }
        mProgressTextView?.let {
            mAnimationHelper.fadeIn(it)
            it.text = message
        }
    }

    override fun hideProgress() {
        mProgressContainer?.let { mAnimationHelper.fadeOut(it) }
        mProgressTextView?.let {
            mAnimationHelper.fadeOut(it)
            it.text = ""
        }
    }

    override fun showError(code: Int?, title: String?, message: String?) {
        mDialogHelper.showError(title, message)
    }

    override fun showMessage(code: Int?, title: String?, message: String?) {
        mDialogHelper.showMessage(title, message)
    }

}
