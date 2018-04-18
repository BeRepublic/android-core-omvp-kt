package com.omvp.app.ui.samples.vibration.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.vibration.view.VibrationView

import javax.inject.Inject

class VibrationPresenterImpl @Inject
constructor(vibrationView: VibrationView) : BasePresenter<VibrationView>(vibrationView), VibrationPresenter {

    override fun onViewLoaded() {
        super.onViewLoaded()
    }
}
