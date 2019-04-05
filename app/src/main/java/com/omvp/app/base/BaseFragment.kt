package com.omvp.app.base

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.omvp.app.base.BaseFragmentModule.Companion.CHILD_FRAGMENT_MANAGER
import com.omvp.app.base.BaseFragmentModule.Companion.FRAGMENT_DIALOG_HELPER
import com.omvp.app.helper.AnimationHelper
import com.omvp.app.helper.DialogHelper
import com.omvp.app.util.DisposableManager
import com.omvp.app.util.TrackerManager
import com.raxdenstudios.commons.util.SDKUtils
import com.raxdenstudios.square.SquareDialogFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

/**
 * Abstract (Dialog)Fragment for all (Dialog)Fragments and child (Dialog)Fragments to extend.
 * This contains some boilerplate dependency injection code and activity [Context].
 *
 *
 * **WHY EXTEND DialogFragment?**
 * [DialogFragment]s are simple extensions of Fragments. DialogFragments can be shown as a
 * dialog floating above the current activity or be embedded into views like regular fragments.
 * Therefore, supporting both Fragments and DialogFragments for dependency injection can simply be
 * achieved by having the base fragment class (this) extend DialogFragment instead of Fragment.
 * We could have separate base classes for Fragments and DialogFragments but that would produce
 * duplicate code. See See https://github.com/vestrel00/android-dagger-butterknife-mvp/pull/64
 *
 *
 * Note that as of Dagger 2.12, the abstract base framework type
 * [dagger.android.DaggerDialogFragment] has been introduced for subclassing if so desired.
 *
 *
 * **DEPENDENCY INJECTION**
 * We could extend [dagger.android.DaggerDialogFragment] so we can get the boilerplate
 * dagger code for free. However, we want to avoid inheritance (if possible and it is in this case)
 * so that we have to option to inherit from something else later on if needed.
 *
 *
 * **VIEW BINDING**
 * This fragment handles view bind and unbinding.
 */
abstract class BaseFragment : SquareDialogFragment(), HasSupportFragmentInjector {

    /**
     * A reference to the activity Context is injected and used instead of the getter method. This
     * enables ease of mocking and verification in tests (in case Activity needs testing).
     * More importantly, the getter method (getContext()) is not available for API level below 23.
     * We could use getActivity() though since that is available since API 11. However, exposing the
     * Activity reference is less safe than just exposing the Context since a lot more can be done
     * with the Activity reference.
     *
     *
     */
    @Inject
    @field:Named(BaseActivityModule.ACTIVITY_CONTEXT)
    lateinit var mContext: Context
    @Inject
    lateinit var mResources: Resources
    @Inject
    @field:Named(BaseFragmentModule.FRAGMENT_ARGUMENTS)
    lateinit var mArguments: Bundle
    @Inject
    lateinit var mAnimationHelper: AnimationHelper
    @Inject
    lateinit var mTrackerManager: TrackerManager
    @Inject
    lateinit var mDisposableManager: DisposableManager
    @Inject
    @field:Named(CHILD_FRAGMENT_MANAGER)
    lateinit var mChildFragmentManager: FragmentManager
    @Inject
    @field:Named(FRAGMENT_DIALOG_HELPER)
    lateinit var mDialogHelper: DialogHelper

    @Inject
    lateinit var mChildFragmentInjector: DispatchingAndroidInjector<Fragment>

    // =============== LifeCycle ===================================================================

    override fun onAttach(activity: Activity?) {
        if (!SDKUtils.hasMarshmallow()) {
            // Perform injection here before M, L (API 22) and below because onAttach(Context)
            // is not yet available at L.
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context?) {
        if (SDKUtils.hasMarshmallow()) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(Activity).
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        mDisposableManager.dispose()
        super.onDestroy()
    }

    // =============== HasFragmentInjector =========================================================

    override fun supportFragmentInjector() = mChildFragmentInjector

    // =============== Support methods =============================================================

    protected fun getDrawable(resourceId: Int) = ContextCompat.getDrawable(mContext, resourceId)

    protected fun getColor(color: Int) = ContextCompat.getColor(mContext, color)

    protected fun addDisposable(disposable: Disposable) = mDisposableManager.add(disposable)

    protected fun removeDisposable(disposable: Disposable) = mDisposableManager.remove(disposable)

    protected fun addChildFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        mChildFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

}
