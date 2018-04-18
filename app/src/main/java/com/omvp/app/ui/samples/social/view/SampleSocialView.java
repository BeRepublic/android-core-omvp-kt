package com.omvp.app.ui.samples.social.view;

import com.omvp.app.base.mvp.view.BaseView;

/**
 * Created by Angel on 21/02/2018.
 */
public interface SampleSocialView extends BaseView {
    void drawImage(String imageUrl);

    void drawName(String name);

    void drawEmail(String email);

    void showHideSocialButtons(boolean show);
}
