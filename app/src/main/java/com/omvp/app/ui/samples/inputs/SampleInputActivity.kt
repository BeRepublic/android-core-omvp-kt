package com.omvp.app.ui.samples.inputs

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.inputs.view.SampleInputFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SampleInputActivity : BaseFragmentActivity(), SampleInputFragment.FragmentCallback, ToolbarInterceptorCallback, InjectFragmentInterceptorCallback<SampleInputFragment> {


    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: SampleInputFragment

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

    override fun onCreateFragment(): SampleInputFragment {
        return SampleInputFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleInputFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
    }

    private fun setUpViews() {

    }
}
