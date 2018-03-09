package com.omvp.app.base.mvp.presenter

import android.content.Context
import android.content.res.Resources
import android.os.Bundle

import com.omvp.app.base.BaseFragmentModule
import com.omvp.app.base.mvp.view.BaseView
import com.omvp.app.base.mvp.view.IView
import com.omvp.app.util.DisposableManager

import javax.inject.Inject
import javax.inject.Named

import io.reactivex.disposables.Disposable


/**
 * Abstract [Presenter] for all presenters to extend.
 *
 *
 * The presenter is responsible to act as the middle man between view and model. It retrieves data
 * from the model and returns it formatted to the view. But unlike the typical MVC, it also decides
 * what happens when you interact with the view. To access view, use mView.
 *
 * @param <TView> the type of the [IView].
</TView> */
abstract class BasePresenter<TView : BaseView>(protected var mView: TView?) : Presenter {

    @Inject
    lateinit var mContext: Context
    @Inject
    lateinit var mResources: Resources
    @Inject
    @Named(BaseFragmentModule.DISPOSABLE_FRAGMENT_MANAGER)
    lateinit var mDisposableManager: DisposableManager

    // =============== LifeCycle ===================================================================

    override fun onViewRestored(savedState: Bundle?) {

    }

    override fun onViewLoaded() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDropView() {

    }

    override fun onSaveView(outState: Bundle) {

    }

    // =============== Support methods =============================================================

    protected fun showError(title: Int, description: Int) {
        showError(0, title, description)
    }

    protected fun showError(title: String, description: String) {
        showError(0, title, description)
    }

    protected fun showError(code: Int, title: Int, description: Int) {
        showError(code, mResources.getString(title), mResources.getString(description))
    }

    protected fun showError(code: Int, title: String, description: String) {
        mView?.showError(code, title, description)
    }

    protected fun showProgress(message: Int) {
        showProgress(0f, message)
    }

    protected fun showProgress(message: String) {
        showProgress(0f, message)
    }

    protected fun showProgress(progress: Float, message: Int) {
        showProgress(progress, mResources.getString(message))
    }

    @JvmOverloads
    protected fun showProgress(progress: Float = 0f, message: String = "") {
        mView?.showProgress(progress, message)
    }

    protected fun hideProgress() {
        mView?.hideProgress()
    }
}