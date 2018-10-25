package com.omvp.app.ui.samples.list

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.list.view.SampleListFragment
import com.omvp.domain.SampleDomain
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SampleListActivity : BaseFragmentActivity(), SampleListFragment.FragmentCallback,
        ToolbarInterceptorCallback, InjectFragmentInterceptorCallback<SampleListFragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: SampleListFragment

    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mFragment.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): SampleListFragment {
        return SampleListFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleListFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
    }

    override fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNavigationHelper.launchDetailWithSharedViewTransition(sampleDomain.id, sharedView)
        } else {
            mNavigationHelper.launchDetail(sampleDomain.id)
        }
    }

}
