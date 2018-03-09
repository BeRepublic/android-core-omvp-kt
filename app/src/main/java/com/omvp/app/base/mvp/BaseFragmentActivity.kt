package com.omvp.app.base.mvp

import android.app.Fragment
import android.app.FragmentManager
import android.support.annotation.IdRes

import com.omvp.app.base.BaseActivity
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback

import javax.inject.Inject

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseFragmentActivity : BaseActivity(),
        BaseViewFragmentCallback {

    /**
     * A reference to the FragmentManager is injected and used instead of the getter method. This
     * enables ease of mocking and verification in tests (in case Activity needs testing).
     */
    @Inject
    lateinit var mFragmentManager: FragmentManager


    // =============== BaseViewFragmentCallback ====================================================

    override fun showProgress(progress: Float, message: String?) {

    }

    override fun hideProgress() {

    }

    override fun showError(code: Int?, title: String?, message: String?) {

    }

    override fun showMessage(code: Int?, title: String?, message: String?) {

    }

    // =============== Support methods =============================================================

    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        mFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

}
