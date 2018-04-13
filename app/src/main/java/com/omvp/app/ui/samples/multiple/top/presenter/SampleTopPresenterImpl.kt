package com.omvp.app.ui.samples.multiple.top.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.multiple.top.view.SampleTopView

import javax.inject.Inject

class SampleTopPresenterImpl
@Inject
internal constructor(sampleTopView: SampleTopView) :
        BasePresenter<SampleTopView>(sampleTopView),
        SampleTopPresenter
