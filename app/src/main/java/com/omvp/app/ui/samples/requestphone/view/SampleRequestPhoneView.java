package com.omvp.app.ui.samples.requestphone.view;

import com.omvp.app.base.mvp.view.BaseView;

public interface SampleRequestPhoneView extends BaseView {

    void showPrefixInputError(String error);

    void showPhoneInputError(String error);

    void showPrefixInputSuccess();

    void showPhoneInputSuccess();

    void drawInputCode(String code);

    void codeReceivedLayoutVisibility(int visibility);
}
