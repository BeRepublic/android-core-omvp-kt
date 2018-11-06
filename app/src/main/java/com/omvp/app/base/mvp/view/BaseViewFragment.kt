package com.omvp.app.base.mvp.view

import android.os.Bundle
import android.view.View
import com.omvp.app.base.BaseFragment
import com.omvp.app.base.mvp.presenter.Presenter
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewDialogFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewInterceptorCallback
import javax.inject.Inject

/**
 * A [BaseFragment] that contains and invokes [Presenter] lifecycle invocations.
 *
 *
 * Lifecycle.MVPFragment   ->      Presenter
 *
 * onSaveInstanceState     ->      onSaveView
 * onViewRestored          ->      onViewLoaded
 * onResume                ->      onResume
 * onPause                 ->      onPause
 * onDestroyView           ->      onDropView
 */
abstract class BaseViewFragment<TPresenter : Presenter, TCallback : BaseViewFragmentCallback> : BaseFragment(),
        AutoInflateViewInterceptorCallback,
        BaseView {

    @Inject
    lateinit var mPresenter: TPresenter
    @Inject
    lateinit var mCallback: TCallback

    // =============== LifeCycle ===================================================================

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter.onSaveView(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        /*
         * The Presenter.onStart method is called in onViewRestored so that the Fragment’s
         * views are bound before the presentation begins. This ensures that no NullPointerException
         * occurs if the Presenter calls an MVPView method that uses a bound view.
         *
         * Furthermore, Fragments that do not return a non-null View in onCreateView will result in
         * onViewRestored not being called. This results in Presenter.onViewLoaded not being
         * invoked. Therefore, no-UI Fragments do not support Presenter-View pairs. We could modify
         * our code to support Presenter-View pairs in no-UI Fragments if needed. However, I will
         * keep things as is since I do not consider it appropriate to have a Presenter-View pair
         * in a no-UI Fragment. Do feel free to disagree and refactor.
         */
        mPresenter.onViewRestored(savedInstanceState)
        view?.let { onViewLoaded(savedInstanceState, it) }
        mPresenter.onViewLoaded()
    }

    open fun onViewLoaded(savedInstanceState: Bundle?, view: View) {}

    override fun onDestroyView() {
        // This lifecycle method still gets called even if onCreateView returns a null view.
        super.onDestroyView()
        mPresenter.onDropView()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
        mTrackerManager.trackScreen(this)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    // =============== BaseView ====================================================================

    override fun showProgress(progress: Float, message: String) {
        mCallback.showProgress(progress, message)
    }

    override fun hideProgress() {
        mCallback.hideProgress()
    }

    override fun showError(code: Int?, title: String?, message: String?) {
        mCallback.showError(code, title, message)
    }

    override fun showMessage(code: Int, title: String, message: String) {
        mCallback.showMessage(code, title, message)
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        interceptorList.add(AutoInflateViewDialogFragmentInterceptor(this, this))
    }

    fun onRestartView() {
        mPresenter.onViewLoaded()
    }
}
