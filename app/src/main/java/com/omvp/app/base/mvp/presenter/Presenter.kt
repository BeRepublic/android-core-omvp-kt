package com.omvp.app.base.mvp.presenter

import android.os.Bundle

/**
 * A presenter that defines its own lifecycle methods.
 */
interface Presenter {

    /**
     * Resumes the presentation. This should be called in the view's (Activity or Fragment)
     * onResume() method.
     */
    fun onResume()

    /**
     * Pauses the presentation. This should be called in the view's Activity or Fragment)
     * onPause() method.
     */
    fun onPause()

    /**
     * Starts the presentation. This should be called in the view's (Activity or Fragment)
     * onCreate() or onViewStatedRestored() method respectively.
     *
     * @param savedState the saved instance state that contains state saved in
     * [.onSaveView]
     */
    fun onViewRestored(savedState: Bundle?)

    /**
     * Starts the presentation. This should be called in the view's (Activity or Fragment)
     * onCreate() or onViewStatedRestored() method respectively.
     *
     */
    fun onViewLoaded()

    /**
     * Save the state of the presentation (if any). This should be called in the view's
     * (Activity or Fragment) onSaveInstanceState().
     *
     * @param outState the out state to save instance state
     */
    fun onSaveView(outState: Bundle)

    /**
     * Ends the presentation. This should be called in the view's (Activity or Fragment)
     * onDestroy() or onDestroyView() method respectively.
     */
    fun onDropView()

}
