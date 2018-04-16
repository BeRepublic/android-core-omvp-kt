package com.omvp.app.ui.samples.list.view

import com.omvp.app.base.mvp.view.BaseView
import com.omvp.app.model.SampleModel
import com.omvp.domain.SampleDomain

interface SampleListView : BaseView {
    fun drawSampleList(sampleModelList: List<SampleModel>)

    fun showEmptyView()

    fun onSampleItemSelected(sampleDomain: SampleDomain)
}
