package com.omvp.app.ui.samples.social.presenter;

import com.omvp.app.base.mvp.presenter.Presenter;

/**
 * Created by Angel on 21/02/2018.
 */

public interface SampleSocialPresenter extends Presenter {
    void continueWithFacebook();

    void continueWithGoogle();

    void logout();
}
