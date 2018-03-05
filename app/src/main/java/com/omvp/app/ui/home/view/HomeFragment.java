package com.omvp.app.ui.home.view;

import android.os.Bundle;
import android.view.View;

import com.omvp.app.R;
import com.omvp.app.base.mvp.view.BaseViewFragment;
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback;
import com.omvp.app.ui.home.presenter.HomePresenter;

import butterknife.OnClick;

public class HomeFragment extends BaseViewFragment<HomePresenter, HomeFragment.FragmentCallback> implements HomeView {

    public interface FragmentCallback extends BaseViewFragmentCallback {
        void onSampleListSelected();
    }

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        bundle = bundle == null ? new Bundle() : bundle;
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.button)
    public void onSampleListClicked(View view) {
        mCallback.onSampleListSelected();
    }
}
