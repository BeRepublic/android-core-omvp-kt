package com.omvp.app.base

import android.app.Fragment
import android.content.res.Resources
import android.os.Bundle
import android.view.View

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.omvp.app.helper.AnimationHelper
import com.omvp.app.helper.DialogHelper
import com.omvp.app.helper.NavigationHelper
import com.omvp.app.helper.SnackBarHelper
import com.omvp.app.interceptor.google.GoogleApiClientInterceptor
import com.omvp.app.interceptor.google.GoogleApiClientInterceptorCallback
import com.omvp.app.interceptor.permission.PermissionActivityInterceptor
import com.omvp.app.interceptor.permission.PermissionInterceptor
import com.omvp.app.interceptor.permission.PermissionInterceptorCallback
import com.omvp.app.util.DisposableManager
import com.omvp.app.util.OperationBroadcastManager
import com.raxdenstudios.square.SquareActivity
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflatelayout.AutoInflateLayoutInterceptorCallback

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import timber.log.Timber

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseActivity : SquareActivity(),
        AutoInflateLayoutInterceptorCallback,
        PermissionInterceptorCallback,
        GoogleApiClientInterceptorCallback,
        HasFragmentInjector {

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
    lateinit var mOperationBroadcastManager: OperationBroadcastManager
    @Inject
    lateinit var mPermissionInterceptor: PermissionInterceptor
    @Inject
    lateinit var mGoogleApiClientInterceptor: GoogleApiClientInterceptor

    @Inject
    internal lateinit var mAutoInflateLayoutInterceptor: AutoInflateLayoutInterceptor

    @Inject
    internal lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected lateinit var mContentView: View

    // =============== LifeCycle ===================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mOperationBroadcastManager.register()
    }

    public override fun onDestroy() {
        super.onDestroy()

        mOperationBroadcastManager.unregister()
        mDisposableManager.dispose()
    }

    // ========= AutoInflateLayoutInterceptorCallback ==============================================

    override fun onContentViewCreated(view: View, savedInstanceState: Bundle?) {
        mContentView = view
    }

    // =============== HasFragmentInjector =========================================================

    override fun fragmentInjector() = mFragmentInjector

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        interceptorList.add(mAutoInflateLayoutInterceptor)
        interceptorList.add(mPermissionInterceptor)
        interceptorList.add(mGoogleApiClientInterceptor)
    }

    // ========= PermissionInterceptorCallback =====================================================

    protected fun requestLocationPermission() {
        mPermissionInterceptor.requestPermission(PermissionActivityInterceptor.Permission.LOCATION)
    }

    protected fun hasLocationPermission() = mPermissionInterceptor.hasPermission(PermissionActivityInterceptor.Permission.LOCATION)

    override fun onPermissionGranted(permission: PermissionActivityInterceptor.Permission) {
        Timber.d("onPermissionGranted %s", permission.toString())
    }

    override fun onPermissionAlreadyGranted(permission: PermissionActivityInterceptor.Permission) {
        Timber.d("onPermissionAlreadyGranted %s", permission.toString())
    }

    override fun onPermissionDenied(permission: PermissionActivityInterceptor.Permission) {
        Timber.d("onPermissionDenied %s", permission.toString())
    }

    override fun onPermissionDeniedForEver(permission: PermissionActivityInterceptor.Permission) {
        Timber.d("onPermissionDeniedForEver %s", permission.toString())
    }

    // ========= GoogleApiClientInterceptorCallback ================================================

    override fun onGoogleApiClientConnected(bundle: Bundle?, googleApiClient: GoogleApiClient) {
        Timber.d("onGoogleApiClientConnected %s", bundle?.toString() ?: "")
    }

    override fun onGoogleApiClientConnectionSuspended(i: Int) {
        Timber.d("onGoogleApiClientConnectionSuspended %d", i)
    }

    override fun onGoogleApiClientConnectionFailed(connectionResult: ConnectionResult) {
        Timber.d("onGoogleApiClientConnectionFailed %s", connectionResult.toString())
    }

}