package com.omvp.app.ui.samples.detail.view

import com.omvp.app.base.mvp.view.BaseView

/**
 * Created by Angel on 21/02/2018.
 */
interface SampleDetailView : BaseView {

    fun drawImage(imageRes: Int)

    fun drawText(text: String)

    fun drawTitle(title: String)

}
