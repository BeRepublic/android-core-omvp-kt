package com.omvp.app.ui.samples.simple.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.simple.view.SampleView
import javax.inject.Inject

class SamplePresenterImpl                               // name of the class
@Inject internal constructor(sampleView: SampleView) :  // constructor inlined with 'super(sampleView)'
        BasePresenter<SampleView>(sampleView),          // extend
        SamplePresenter {                               // implementations

    override fun onViewLoaded() {
        super.onViewLoaded()

    }

}
