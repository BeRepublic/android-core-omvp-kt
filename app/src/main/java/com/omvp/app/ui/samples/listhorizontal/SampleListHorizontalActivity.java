package com.omvp.app.ui.samples.listhorizontal;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.omvp.app.R;
import com.omvp.app.base.mvp.BaseFragmentActivity;
import com.omvp.app.ui.samples.listhorizontal.view.SampleListHorizontalFragment;
import com.omvp.domain.SampleDomain;
import com.raxdenstudios.square.interceptor.Interceptor;
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor;
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback;
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor;
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback;

import java.util.List;

import javax.inject.Inject;

public class SampleListHorizontalActivity extends BaseFragmentActivity implements
        SampleListHorizontalFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        InjectFragmentInterceptorCallback<SampleListHorizontalFragment> {

    @Inject
    ToolbarInterceptor mToolbarInterceptor;
    @Inject
    InjectFragmentInterceptor mInjectFragmentInterceptor;

    private Toolbar mToolbar;
    private SampleListHorizontalFragment mFragment;

    // =============== ToolbarInterceptorCallback ==================================================

    @Override
    public Toolbar onCreateToolbarView(Bundle savedInstanceState) {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void onToolbarViewCreated(Toolbar toolbar) {
        mToolbar = toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    @Override
    public View onLoadFragmentContainer(Bundle savedInstanceState) {
        return findViewById(R.id.content);
    }

    @Override
    public SampleListHorizontalFragment onCreateFragment() {
        return SampleListHorizontalFragment.Companion.newInstance(mExtras);
    }

    @Override
    public void onFragmentLoaded(SampleListHorizontalFragment fragment) {
        mFragment = fragment;
    }

    // =============== Support methods =============================================================

    @Override
    protected void setupInterceptors(List<Interceptor> interceptorList) {
        super.setupInterceptors(interceptorList);
        interceptorList.add(mToolbarInterceptor);
        interceptorList.add(mInjectFragmentInterceptor);
    }

    @Override
    public void onSampleItemSelected(SampleDomain sampleDomain, View sharedView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNavigationHelper.launchDetailWithSharedViewTransition(sampleDomain.getId(), sharedView);
        } else {
            mNavigationHelper.launchDetail(sampleDomain.getId());
        }
    }

}
