package com.omvp.app.ui.samples.requestphone.view;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.omvp.app.R;
import com.omvp.app.base.mvp.view.BaseViewFragment;
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback;
import com.omvp.app.ui.samples.requestphone.presenter.SampleRequestPhonePresenterImpl;
import com.omvp.components.InputLayoutView;

import butterknife.BindView;
import butterknife.OnClick;

public class SampleRequestPhoneFragment extends BaseViewFragment<SampleRequestPhonePresenterImpl, SampleRequestPhoneFragment.FragmentCallback>
        implements SampleRequestPhoneView {

    @BindView(R.id.input_prefix)
    InputLayoutView mInputPrefixView;
    @BindView(R.id.input_phone)
    InputLayoutView mInputPhoneView;
    @BindView(R.id.code_received_layout)
    RelativeLayout mCodeReceivedLayout;
    @BindView (R.id.input_code)
    InputLayoutView mInputCodeToSent;
    @BindView(R.id.send)
    AppCompatTextView mButtonGetSent;
    @BindView(R.id.sign_out)
    AppCompatTextView mButtonSignOut;

    public interface FragmentCallback extends BaseViewFragmentCallback {
        void signOut();
    }

    public static SampleRequestPhoneFragment newInstance(Bundle bundle) {
        SampleRequestPhoneFragment fragment = new SampleRequestPhoneFragment();
        bundle = bundle == null ? new Bundle() : bundle;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        setupViews();
    }

    private void setupViews() {

    }

    @OnClick(R.id.send)
    public void onValidatePhoneClick(View view) {
        mPresenter.validatePhone(mInputPrefixView.getText(), mInputPhoneView.getText());
    }

    @OnClick(R.id.sign_out)
    public void onSignOutClick(View view) {
       if (mCallback != null) {
           mCallback.signOut ();
       }
    }

    @Override
    public void showPrefixInputError(String error) {
        mInputPrefixView.setError(error);
    }

    @Override
    public void showPhoneInputError(String error) {
        mInputPhoneView.setError(error);
    }

    @Override
    public void showPrefixInputSuccess() {
        mInputPrefixView.setSuccess();
    }

    @Override
    public void showPhoneInputSuccess() {
        mInputPhoneView.setSuccess();
    }

    @Override
    public void drawInputCode(String code) {
        mInputCodeToSent.setText(code);
    }

    @Override
    public void codeReceivedLayoutVisibility(int visibility) {
        mCodeReceivedLayout.setVisibility(visibility);
    }

    public void changeButtonText (String title) {
        mButtonGetSent.setText(title);
    }

    public void singOutButtonVisibility (int visibility) {
        mButtonSignOut.setVisibility(visibility);
    }

}
