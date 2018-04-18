package com.omvp.app.ui.samples.requestphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.omvp.app.R;
import com.omvp.app.base.mvp.BaseFragmentActivity;
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptor;
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptorCallback;
import com.omvp.app.ui.samples.requestphone.view.SampleRequestPhoneFragment;
import com.raxdenstudios.square.interceptor.Interceptor;
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor;
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback;
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor;
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback;

import java.util.List;

import javax.inject.Inject;

public class SampleRequestPhoneActivity extends BaseFragmentActivity implements
        SampleRequestPhoneFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        AuthPhoneInterceptorCallback,
        InjectFragmentInterceptorCallback<SampleRequestPhoneFragment> {

    @Inject
    ToolbarInterceptor mToolbarInterceptor;
    @Inject
    AuthPhoneInterceptor mAuthPhoneInterceptor;
    @Inject
    InjectFragmentInterceptor mInjectFragmentInterceptor;


    private Toolbar mToolbar;
    private SampleRequestPhoneFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    @Override
    public View onLoadFragmentContainer(Bundle savedInstanceState) {
        return findViewById(R.id.content);
    }

    @Override
    public SampleRequestPhoneFragment onCreateFragment() {
        return SampleRequestPhoneFragment.newInstance(mExtras);
    }

    @Override
    public void onFragmentLoaded(SampleRequestPhoneFragment fragment) {
        mFragment = fragment;
    }

    // =============== ToolbarInterceptorCallback ==================================================

    @Override
    public Toolbar onCreateToolbarView(Bundle savedInstanceState) {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void onToolbarViewCreated(Toolbar toolbar) {
        mToolbar = toolbar;
    }


    // =============== Support methods =============================================================

    @Override
    protected void setupInterceptors(List<Interceptor> interceptorList) {
        super.setupInterceptors(interceptorList);
        interceptorList.add(mToolbarInterceptor);
        interceptorList.add(mInjectFragmentInterceptor);
        interceptorList.add(mAuthPhoneInterceptor);
    }

    // =============== AuthPhoneInterceptorCallback methods  =======================================

    @Override
    public void authPhoneUserRetrieved(FirebaseUser firebaseUser) {
        hideProgress();
      if (firebaseUser != null) {

      }
    }

    @Override
    public void authPhoneCodeRetrieved(String code) {
        Toast.makeText(this,getString(R.string.code_retrieved) + code, Toast.LENGTH_LONG ).show();
        mFragment.codeReceivedLayoutVisibility(View.VISIBLE);
        mFragment.changeButtonText("SENT");
        mFragment.drawInputCode(code);
    }

    @Override
    public void authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState authPhoneState) {
        switch (authPhoneState) {
            case STATE_CODE_SENT:
                hideProgress();
                break;
            case STATE_INITIALIZED:
                hideProgress();
                showInitFragmentLayout ();
                break;
            case STATE_SIGNIN_FAILED:
                hideProgress();
                break;
            case STATE_SIGNIN_SUCCESS:
                hideProgress();
                authPhoneSignInSuccess();
                break;
            case STATE_VERIFY_FAILED:
                hideProgress();
                break;
            case STATE_VERIFY_SUCCESS:
                hideProgress();
                break;
        }
    }

    private void showInitFragmentLayout() {
        mFragment.codeReceivedLayoutVisibility(View.GONE);
        mFragment.changeButtonText("GET");
        mFragment.singOutButtonVisibility (View.GONE);
        Toast.makeText(this,getString(R.string.sign_out_success), Toast.LENGTH_LONG ).show();
    }

    private void authPhoneSignInSuccess() {
        mFragment.singOutButtonVisibility (View.VISIBLE);
        Toast.makeText(this,getString(R.string.sign_in_success), Toast.LENGTH_LONG ).show();
    }

    @Override
    public void authPhoneError(AuthPhoneInterceptor.AuthPhoneError authPhoneError) {
        hideProgress();
        switch (authPhoneError) {
            case INSTANT_VALIDATION:
                break;
            case INVALID_CODE:
                showError(0, getString(R.string.unespected_error_title), getString(R.string.invalid_code));
                break;
            case INVALID_PHONE_NUMBER:
                showError(0, getString(R.string.unespected_error_title), getString(R.string.invalid_phone));
                break;
            case QUOTA_EXCEEDED:
                showError(0, getString(R.string.unespected_error_title), getString(R.string.quota_exceeded));
                break;
        }
    }

    @Override
    public void signOut() {
        showProgress(0, "Loading");
        mAuthPhoneInterceptor.signOut();
    }
}
