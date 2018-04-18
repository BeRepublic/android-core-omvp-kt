package com.omvp.app.ui.samples.social.view

import com.omvp.app.base.mvp.view.BaseView

interface SampleSocialView : BaseView {

    fun drawImage (imageUrl: String)

    fun drawName(name: String)

    fun drawEmail(email: String)

    fun showHideSocialButtons(show: Boolean)
}
