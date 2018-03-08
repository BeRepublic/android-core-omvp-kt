package com.omvp.app.ui.samples.samplemultiple.bottom.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.samplemultiple.bottom.view.SampleBottomView

import javax.inject.Inject

class SampleBottomPresenterImpl
@Inject
internal constructor(sampleBottomView: SampleBottomView) :
        BasePresenter<SampleBottomView>(sampleBottomView),
        SampleBottomPresenter