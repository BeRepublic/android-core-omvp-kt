package com.omvp.app.ui.samples.pager.view

import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.pager.presenter.SamplePagerPresenter

abstract class SamplePagerFragment<TPresenter : SamplePagerPresenter, TCallback : SamplePagerFragment.SamplePagerFragmentCallback> :
        BaseViewFragment<TPresenter, TCallback>(),
        SamplePagerView {

    interface SamplePagerFragmentCallback : BaseViewFragmentCallback

}
