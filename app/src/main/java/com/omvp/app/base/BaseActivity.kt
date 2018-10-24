package com.omvp.app.base

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
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
    lateinit var mOperationBroadcastInterceptor: OperationBroadcastInterceptor
    @Inject
    internal lateinit var mAutoInflateLayoutInterceptor: AutoInflateLayoutInterceptor

    @Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected lateinit var mContentView: View

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
}