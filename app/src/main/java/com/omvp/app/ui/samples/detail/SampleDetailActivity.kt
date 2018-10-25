package com.omvp.app.ui.samples.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.detail.view.SampleDetailFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SampleDetailActivity : BaseFragmentActivity(), SampleDetailFragment.FragmentCallback,
        ToolbarInterceptorCallback, InjectFragmentInterceptorCallback<SampleDetailFragment> {


    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mAppBarLayout: AppBarLayout
    private lateinit var mImageView: AppCompatImageView
    private lateinit var mFragment: SampleDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViews()
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

    override fun onCreateFragment(): SampleDetailFragment {
        return SampleDetailFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleDetailFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
    }

    private fun setUpViews() {
        mAppBarLayout = findViewById(R.id.app_bar_layout)
        mImageView = findViewById(R.id.image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.transitionName = "item"
            postponeEnterTransition()
        }
    }

    override fun drawImage(imageRes: Int) {
        mImageView.setImageResource(imageRes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition()
        }
    }
}
