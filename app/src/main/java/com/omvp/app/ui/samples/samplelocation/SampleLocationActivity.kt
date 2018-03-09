package com.omvp.app.ui.samples.samplelocation

import android.location.Location
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.google.android.gms.common.api.GoogleApiClient
import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.interceptor.location.LocationInterceptor
import com.omvp.app.interceptor.location.LocationInterceptorCallback
import com.omvp.app.interceptor.permission.PermissionActivityInterceptor
import com.omvp.app.ui.samples.samplelocation.view.SampleLocationFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

import timber.log.Timber

class SampleLocationActivity : BaseFragmentActivity(),
        SampleLocationFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        LocationInterceptorCallback,
        InjectFragmentInterceptorCallback<SampleLocationFragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor
    @Inject
    internal lateinit var mLocationInterceptor: LocationInterceptor


    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: SampleLocationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
    }


    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): SampleLocationFragment {
        return SampleLocationFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleLocationFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
        interceptorList.add(mLocationInterceptor)
    }


    // =============== LocationInterceptorCallback =================================================


    override fun onLocationError(status: Int) {
        Timber.d("onLocationError %d", status)
        locationChanged(null)
    }

    override fun onLocationChanged(location: Location) {
        Timber.d("onLocationChanged %s", location.toString())
        locationChanged(location)
    }


    // ========= GoogleApiClientInterceptorCallback ================================================

    override fun onGoogleApiClientConnected(bundle: Bundle?, googleApiClient: GoogleApiClient) {
        mLocationInterceptor.setGoogleApiClient(googleApiClient)
    }

    // =============== PermissionInterceptorCallback ===============================================

    override fun onPermissionGranted(permission: PermissionActivityInterceptor.Permission) {
        mLocationInterceptor.requestLocationUpdates()
    }

    override fun onPermissionAlreadyGranted(permission: PermissionActivityInterceptor.Permission) {
        mLocationInterceptor.requestLocationUpdates()
    }


    private fun locationChanged(location: Location?) {
        mFragment.locationChanged(location)
    }
}
