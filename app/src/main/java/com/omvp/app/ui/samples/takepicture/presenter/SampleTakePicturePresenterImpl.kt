package com.omvp.app.ui.samples.takepicture.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.takepicture.view.SampleTakePictureView

import javax.inject.Inject

class SampleTakePicturePresenterImpl
@Inject
internal constructor(sampleView: SampleTakePictureView) : BasePresenter<SampleTakePictureView>(sampleView),
        SampleTakePicturePresenter
