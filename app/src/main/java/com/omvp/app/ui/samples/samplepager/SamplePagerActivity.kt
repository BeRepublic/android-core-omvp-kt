package com.omvp.app.ui.samples.samplepager

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.samplepager.view.SamplePagerFirstFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerFragment
import com.omvp.app.ui.samples.samplepager.view.SamplePagerSecondFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class SamplePagerActivity : BaseFragmentActivity(),
        SamplePagerFirstFragment.SamplePagerFirstFragmentCallback,
        SamplePagerSecondFragment.SamplePagerSecondFragmentCallback,
        ToolbarInterceptorCallback,
        FragmentStatePagerInterceptorCallback<SamplePagerFragment<*, *>> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mFragmentStatePagerInterceptor: FragmentStatePagerInterceptor<*>

    private lateinit var mToolbar: Toolbar
    private lateinit var mViewPager: ViewPager
    private lateinit var mFirstFragment: SamplePagerFirstFragment
    private lateinit var mSecondFragment: SamplePagerSecondFragment

    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    // =============== InjectFragmentStatePagerInterceptorCallback ===========================================

    override fun onCreateViewPager(savedInstanceState: Bundle?): ViewPager {
        return findViewById(R.id.content)
    }

    override fun onViewPagerCreated(viewPager: ViewPager) {
        mViewPager = viewPager
    }

    override fun onCreateFragment(position: Int): SamplePagerFragment<*, *>? {
        return when (position) {
            0 -> SamplePagerFirstFragment.newInstance(intent.extras)
            1 -> SamplePagerSecondFragment.newInstance(intent.extras)
            else -> {
                null
            }
        }
    }

    override fun onFragmentLoaded(fragment: SamplePagerFragment<*, *>, position: Int) {
        when (position) {
            0 -> mFirstFragment = fragment as SamplePagerFirstFragment
            1 -> mSecondFragment = fragment as SamplePagerSecondFragment
        }
    }

    override fun onFragmentSelected(fragment: SamplePagerFragment<*, *>, position: Int) {

    }

    override fun getViewPagerElements(): Int {
        return 2
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mFragmentStatePagerInterceptor)
    }
}
