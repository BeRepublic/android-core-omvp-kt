package com.omvp.app.ui.samples.sampletakepicture

import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.interceptor.permission.PermissionActivityInterceptor
import com.omvp.app.interceptor.takePicture.TakePictureInterceptor
import com.omvp.app.interceptor.takePicture.TakePictureInterceptorCallback
import com.omvp.app.ui.samples.sampletakepicture.view.SampleTakePictureFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SampleTakePictureActivity : BaseFragmentActivity(),
        SampleTakePictureFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        TakePictureInterceptorCallback,
        InjectFragmentInterceptorCallback<SampleTakePictureFragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor
    @Inject
    internal lateinit var mTakePictureInterceptor: TakePictureInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: SampleTakePictureFragment

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

    override fun onCreateFragment(): SampleTakePictureFragment {
        return SampleTakePictureFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleTakePictureFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
        interceptorList.add(mTakePictureInterceptor)
    }

    // ========= PermissionInterceptorCallback =====================================================
    override fun onPermissionGranted(permission: PermissionActivityInterceptor.Permission) {
        super.onPermissionGranted(permission)
        if (permission == PermissionActivityInterceptor.Permission.CAMERA) {
            mTakePictureInterceptor.takePicture(resources.getString(R.string.select_a_picture))
        }
    }

    override fun onPermissionAlreadyGranted(permission: PermissionActivityInterceptor.Permission) {
        super.onPermissionAlreadyGranted(permission)
        if (permission == PermissionActivityInterceptor.Permission.CAMERA) {
            mTakePictureInterceptor.takePicture(resources.getString(R.string.select_a_picture))
        }
    }


    // ========= TakePictureInterceptorCallback ====================================================
    override fun onWorkingPictureProgress(workingProgress: Boolean) {
        if (workingProgress) {
            showProgress(0f, "")
        } else {
            hideProgress()
        }
    }

    override fun onPictureRetrieved(uri: Uri) {
        mFragment.pictureRetrieved(uri)
    }

    override fun onGalleryImageRequested() {
        if (mPermissionInterceptor.hasPermission(PermissionActivityInterceptor.Permission.CAMERA)) {
            mTakePictureInterceptor.takePicture(resources.getString(R.string.select_a_picture))
        } else {
            mPermissionInterceptor.requestPermission(PermissionActivityInterceptor.Permission.CAMERA)
        }
    }
}
