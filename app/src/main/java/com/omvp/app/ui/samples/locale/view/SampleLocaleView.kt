package com.omvp.app.ui.samples.locale.view

import com.omvp.app.base.mvp.view.BaseView

/**
 * Created by Angel on 21/02/2018.
 */
interface SampleLocaleView : BaseView {

    fun drawLocale(locale: String)

    fun drawLocaleSelector(localeList: List<String>, selection: Int)

}
