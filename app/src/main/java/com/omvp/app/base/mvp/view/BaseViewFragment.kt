package com.omvp.app.base.mvp.view

import android.os.Bundle

import com.omvp.app.base.BaseFragment
import com.omvp.app.base.mvp.presenter.Presenter
import com.omvp.app.util.TrackerManager
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewInterceptor
import com.raxdenstudios.square.interceptor.commons.autoinflateview.AutoInflateViewInterceptorCallback

import javax.inject.Inject

import butterknife.ButterKnife
import butterknife.Unbinder
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsInterceptor
import com.raxdenstudios.square.interceptor.commons.handlearguments.HandleArgumentsInterceptorCallback

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
        HandleArgumentsInterceptorCallback,
        BaseView {

    @Inject
    lateinit var mPresenter: TPresenter
    @Inject
    lateinit var mCallback: TCallback
    @Inject
    lateinit var mTrackerManager: TrackerManager

    @Inject
    internal lateinit var mAutoInflateViewInterceptor: AutoInflateViewInterceptor
    @Inject
    internal lateinit var mHandleArgumentsInterceptor: HandleArgumentsInterceptor

    private var mUnbinder: Unbinder? = null

    // =============== LifeCycle ===================================================================

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mPresenter.onSaveView(outState)
    }

    override fun onHandleArguments(savedInstanceState: Bundle?, arguments: Bundle?) {
        mPresenter.onHandleArguments(savedInstanceState, arguments)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        /*
         * Bind the views here instead of in onViewCreated so that view state changed listeners
         * are not invoked automatically without user interaction.
         *
         * If we bind before this method (e.g. onViewCreated), then any checked changed
         * listeners bound by ButterKnife will be invoked during fragment recreation (since
         * Android itself saves and restores the views' states. Take a look at this gist for a
         * concrete example: https://gist.github.com/vestrel00/982d585144423f728342787341fa001d
         *
         * The lifecycle order is as follows (same if added via xml or java or if retain
         * instance is true):
         *
         * onAttach
         * onCreateView
         * onViewCreated
         * onActivityCreated
         * onViewRestored
         * onStart
         * onResume
         *
         * Note that the onCreate (and other lifecycle events) are omitted on purpose. The
         * caveat to this approach is that views, listeners, and resources bound by
         * Butterknife will be null until onViewStatedRestored. Just be careful not to use any
         * objects bound using Butterknife before onViewRestored.
         *
         * Fragments that do not return a non-null View in onCreateView results in onViewCreated
         * and onViewRestored not being called. This means that Butterknife.bind will not get
         * called, which is completely fine because there is no View to bind. Furthermore, there is
         * no need to check if getView() returns null here because this lifecycle method only gets
         * called with a non-null View.
         */
        mUnbinder = ButterKnife.bind(this, view!!)
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
        mPresenter.onViewLoaded()
    }

    override fun onDestroyView() {
        // This lifecycle method still gets called even if onCreateView returns a null view.
        mUnbinder?.unbind()
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
        interceptorList.add(mAutoInflateViewInterceptor)
        interceptorList.add(mHandleArgumentsInterceptor)
    }

}
