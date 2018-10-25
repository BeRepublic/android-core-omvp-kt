package com.omvp.app.ui.samples.multiple

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.multiple.bottom.view.SampleBottomFragment
import com.omvp.app.ui.samples.multiple.top.view.SampleTopFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragmentlist.InjectFragmentListInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragmentlist.InjectFragmentListInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SampleMultipleActivity : BaseFragmentActivity(),
        SampleTopFragment.FragmentCallback,
        SampleBottomFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        InjectFragmentListInterceptorCallback<Fragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentListInterceptor: InjectFragmentListInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mTopFragment: SampleTopFragment
    private lateinit var mBottomFragment: SampleBottomFragment


    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    // =============== SampleTopFragment.FragmentCallback ==========================================


    // =============== InjectFragmentsInterceptorCallback ===========================================

    override fun getFragmentCount(): Int {
        return 2
    }

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?, position: Int): View? {
        return when (position) {
            0 -> findViewById(R.id.top_content)
            1 -> findViewById(R.id.bottom_content)
            else -> {
                null
            }
        }
    }

    override fun onCreateFragment(position: Int): Fragment? {
        return when (position) {
            0 -> SampleTopFragment.newInstance(intent.extras)
            1 -> SampleBottomFragment.newInstance(intent.extras)
            else -> {
                null
            }
        }
    }

    override fun onFragmentLoaded(fragment: Fragment, position: Int) {
        when (position) {
            0 -> mTopFragment = fragment as SampleTopFragment
            1 -> mBottomFragment = fragment as SampleBottomFragment
        }
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentListInterceptor)
    }

}
