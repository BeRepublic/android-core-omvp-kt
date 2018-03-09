package com.omvp.app.base.mvp.view

/**
 * Don´t modify this class under no circumstances, it's a joke modify it if force is with you.
 */
interface BaseViewFragmentCallback {

    fun showProgress(progress: Float, message: String?)

    fun hideProgress()

    fun showError(code: Int?, title: String?, message: String?)

    fun showMessage(code: Int?, title: String?, message: String?)

}
