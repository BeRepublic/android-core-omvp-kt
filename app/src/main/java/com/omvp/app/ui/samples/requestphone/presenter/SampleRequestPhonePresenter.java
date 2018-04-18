package com.omvp.app.ui.samples.requestphone.presenter;

import com.omvp.app.base.mvp.presenter.Presenter;

public interface SampleRequestPhonePresenter extends Presenter {

    void validatePhone(final String prefix, final String phone);

}
