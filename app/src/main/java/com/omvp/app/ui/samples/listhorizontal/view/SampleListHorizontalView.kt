package com.omvp.app.ui.samples.listhorizontal.view

import android.view.View

import com.omvp.app.base.mvp.view.BaseView
import com.omvp.app.model.SampleModel
import com.omvp.domain.SampleDomain

interface SampleListHorizontalView : BaseView {
    fun drawSampleList(sampleModelList: List<Any>)

    fun showEmptyView()

    fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View?)

    fun drawRemoveAnimation(position: Int)

    fun drawAddAnimation(model: SampleModel)
}
