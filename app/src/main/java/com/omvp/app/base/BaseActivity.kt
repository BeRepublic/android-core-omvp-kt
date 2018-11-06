package com.omvp.app.base

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omvp.app.helper.AnimationHelper
import com.omvp.app.helper.DialogHelper
import com.omvp.app.helper.NavigationHelper
import com.omvp.app.helper.SnackBarHelper
import com.omvp.app.interceptor.operation.OperationBroadcastInterceptor
import com.omvp.app.util.DisposableManager
import com.raxdenstudios.square.SquareActivity
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptorCallback
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseActivity : SquareActivity(),
        AutoInflateLayoutInterceptorCallback,
        HasSupportFragmentInjector {

    @Inject
    lateinit var mResources: Resources
    @Inject
    lateinit var mExtras: Bundle
    @Inject
    lateinit var mNavigationHelper: NavigationHelper
    @Inject
    lateinit var mDialogHelper: DialogHelper
    @Inject
    lateinit var mSnackBarHelper: SnackBarHelper
    @Inject
    lateinit var mAnimationHelper: AnimationHelper
    @Inject
    lateinit var mDisposableManager: DisposableManager
    @Inject
    lateinit var mFragmentManager: FragmentManager
    @Inject
    lateinit var mOperationBroadcastInterceptor: OperationBroadcastInterceptor
    @Inject
    internal lateinit var mAutoInflateLayoutInterceptor: AutoInflateLayoutInterceptor

    @Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mContentView: View

    // =============== LifeCycle ===================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    public override fun onDestroy() {
        super.onDestroy()
        mDisposableManager.dispose()
    }

    // ========= AutoInflateLayoutInterceptorCallback ==============================================

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        mContentView = view
    }

    // =============== HasFragmentInjector =========================================================

    override fun supportFragmentInjector() = mFragmentInjector

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        interceptorList.add(mAutoInflateLayoutInterceptor)
        interceptorList.add(mOperationBroadcastInterceptor)
    }

    fun finishWithResultOK() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    fun finishWithResultOK(data: Intent) {
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) = mFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment)
            .commit()
}