package com.omvp.app.ui.samples.bottomnavigation

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationFirstFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationSecondFragment
import com.omvp.app.ui.samples.bottomnavigation.view.BottomNavigationThirdFragment
import com.omvp.components.PagerIndicator
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptor
import com.raxdenstudios.square.interceptor.commons.fragmentstatepager.FragmentStatePagerInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback
import kotlinx.android.synthetic.main.bottom_navigation_activity.*
import javax.inject.Inject

class BottomNavigationActivity : BaseFragmentActivity(),
        BottomNavigationFirstFragment.BottomNavigationFirstFragmentCallback,
        BottomNavigationSecondFragment.BottomNavigationSecondFragmentCallback,
        BottomNavigationThirdFragment.BottomNavigationThirdFragmentCallback,
        ToolbarInterceptorCallback,
        FragmentStatePagerInterceptorCallback<BottomNavigationFragment<*, *>> {

    override val viewPagerElements: Int
        get() = 3

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mFragmentStatePagerInterceptor: FragmentStatePagerInterceptor<*>

    private var mPagerIndicator: PagerIndicator? = null

    private lateinit var mToolbar: Toolbar
    private lateinit var mViewPager: ViewPager
    private lateinit var mFirstFragment: BottomNavigationFirstFragment
    private lateinit var mSecondFragment: BottomNavigationSecondFragment
    private lateinit var mThirdFragment: BottomNavigationThirdFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPagerIndicator = findViewById(R.id.pager_indicator)
    }

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
        mPagerIndicator?.setViewPager(viewPager)

        bottom_view.setViewPager(mViewPager, mIconResArray)
    }

    override fun onCreateFragment(position: Int): BottomNavigationFragment<*, *>? {
        return when (position) {
            0 -> BottomNavigationFirstFragment.newInstance(intent.extras)
            1 -> BottomNavigationSecondFragment.newInstance(intent.extras)
            2 -> BottomNavigationThirdFragment.newInstance(intent.extras)
            else -> null
        }
    }

    override fun onFragmentLoaded(fragment: BottomNavigationFragment<*, *>?, position: Int) {
        when (position) {
            0 -> mFirstFragment = fragment as BottomNavigationFirstFragment
            1 -> mSecondFragment = fragment as BottomNavigationSecondFragment
            2 -> mThirdFragment = fragment as BottomNavigationThirdFragment
        }
    }

    override fun onFragmentSelected(fragment: BottomNavigationFragment<*, *>?, position: Int) {

    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mFragmentStatePagerInterceptor)
    }

    override fun onIncrementSelected(position: Int) {
        bottom_view.incrementCounterAt(position)
    }

    companion object {

        private val mIconResArray = intArrayOf(
                R.drawable.bottom_navigation_item_home,
                R.drawable.bottom_navigation_item_home,
                R.drawable.bottom_navigation_item_home
        )
    }
}
