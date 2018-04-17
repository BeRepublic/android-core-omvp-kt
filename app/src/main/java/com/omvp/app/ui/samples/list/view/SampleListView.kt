package com.omvp.app.ui.samples.list.view

import android.view.View
import com.omvp.app.base.mvp.view.BaseView
import com.omvp.app.model.SampleModel
import com.omvp.domain.SampleDomain

interface SampleListView : BaseView {
    fun drawSampleList(sampleModelList: List<SampleModel>)

    fun showEmptyView()

    fun onSampleItemSelected(sampleDomain: SampleDomain, sharedView: View)

    fun drawRemoveAnimation(position: Int)

    fun drawAddAnimation(model: SampleModel)

    fun drawViewMoved(oldPosition: Int, newPosition: Int)

    fun drawViewSwiped(position: Int)

    fun drawUpdatedItems(updatedList: List<SampleModel>)
}
