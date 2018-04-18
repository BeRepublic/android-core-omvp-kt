package com.omvp.app.ui.samples.social.presenter

import com.omvp.app.base.mvp.presenter.Presenter

interface SampleSocialPresenter : Presenter {

    fun continueWithFacebook()

    fun continueWithGoogle ()

    fun logout()
}
