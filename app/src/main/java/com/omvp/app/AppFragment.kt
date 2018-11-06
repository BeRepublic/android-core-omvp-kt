package com.omvp.app

import android.app.Activity
import android.content.Intent

import com.omvp.app.base.mvp.presenter.Presenter
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.commons.Constants

open class AppFragment<TPresenter : Presenter, TCallback : BaseViewFragmentCallback> : BaseViewFragment<TPresenter, TCallback>() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_REFRESH) {
            if (resultCode == Activity.RESULT_OK) {
                onRestartView()
            }
        }
    }

}
