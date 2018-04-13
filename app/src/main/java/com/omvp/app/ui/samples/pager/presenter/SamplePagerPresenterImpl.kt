package com.omvp.app.ui.samples.pager.presenter


import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.ui.samples.pager.view.SamplePagerView

abstract class SamplePagerPresenterImpl<TView : SamplePagerView>(view: TView) : BasePresenter<TView>(view),
        SamplePagerPresenter
