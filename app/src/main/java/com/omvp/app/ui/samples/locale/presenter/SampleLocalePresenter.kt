package com.omvp.app.ui.samples.locale.presenter

import com.omvp.app.base.mvp.presenter.Presenter

/**
 * Created by Angel on 21/02/2018.
 */

interface SampleLocalePresenter : Presenter {

    fun localeSelected(position: Int)

    fun changeLocale()

}
