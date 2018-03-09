package com.omvp.app.base.mvp.view

/**
 * Abstract [IView] for all views to extend.
 */
interface BaseView : IView {

    fun showProgress(progress: Float, message: String)

    fun hideProgress()

    fun showError(code: Int, title: String, message: String)

    fun showMessage(code: Int, title: String, message: String)

}
