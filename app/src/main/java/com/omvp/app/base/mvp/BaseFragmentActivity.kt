package com.omvp.app.base.mvp

import android.app.Fragment
import android.app.FragmentManager
import android.support.annotation.IdRes
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.omvp.app.R
import com.omvp.app.base.BaseActivity
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.dialog.notice.view.NoticeDialogFragment
import javax.inject.Inject

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseFragmentActivity : BaseActivity(),
        NoticeDialogFragment.FragmentCallback,
        BaseViewFragmentCallback {

    /**
     * A reference to the FragmentManager is injected and used instead of the getter method. This
     * enables ease of mocking and verification in tests (in case Activity needs testing).
     */
    @Inject
    lateinit var mFragmentManager: FragmentManager

    private var mProgressContainer: View? = null
    private var mProgressTextView: AppCompatTextView? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        mProgressContainer = findViewById(R.id.progress)
        mProgressTextView = findViewById(R.id.progress_label)
    }

    // =============== BaseViewFragmentCallback ====================================================

    override fun showProgress(progress: Float, message: String?) {
        if (mProgressContainer != null) {
            mAnimationHelper.fadeIn(mProgressContainer!!)

            if (mProgressTextView != null) {
                mAnimationHelper.fadeIn(mProgressTextView!!)
                mProgressTextView!!.text = message
            }
        }
    }

    override fun hideProgress() {
        if (mProgressContainer != null) {
            mAnimationHelper.fadeOut(mProgressContainer!!)

            if (mProgressTextView != null) {
                mAnimationHelper.fadeOut(mProgressTextView!!)
                mProgressTextView!!.text = ""
            }
        }
    }

    override fun showError(code: Int?, title: String?, message: String?) {
        mDialogHelper.showError(title, message)
    }

    override fun showMessage(code: Int?, title: String?, message: String?) {
        mDialogHelper.showMessage(title, message)
    }

    // =============== Support methods =============================================================

    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        mFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

}
