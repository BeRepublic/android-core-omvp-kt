package com.omvp.app.ui.samples.requestphone

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseUser
import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptor
import com.omvp.app.interceptor.authPhone.AuthPhoneInterceptorCallback
import com.omvp.app.ui.samples.requestphone.view.SampleRequestPhoneFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback
import javax.inject.Inject

class SampleRequestPhoneActivity : BaseFragmentActivity(), SampleRequestPhoneFragment.FragmentCallback, ToolbarInterceptorCallback, AuthPhoneInterceptorCallback, InjectFragmentInterceptorCallback<SampleRequestPhoneFragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mAuthPhoneInterceptor: AuthPhoneInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor


    private var mToolbar: Toolbar? = null
    private var mFragment: SampleRequestPhoneFragment? = null

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): SampleRequestPhoneFragment {
        return SampleRequestPhoneFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: SampleRequestPhoneFragment) {
        mFragment = fragment
    }

    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }


    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
        interceptorList.add(mAuthPhoneInterceptor)
    }

    // =============== AuthPhoneInterceptorCallback methods  =======================================

    override fun authPhoneUserRetrieved(firebaseUser: FirebaseUser) {
        hideProgress()
    }

    override fun authPhoneCodeRetrieved(code: String) {
        Toast.makeText(this, getString(R.string.code_retrieved) + code, Toast.LENGTH_LONG).show()
        mFragment?.codeReceivedLayoutVisibility(View.VISIBLE)
        mFragment?.changeButtonText("SENT")
        mFragment?.drawInputCode(code)
    }

    override fun authPhoneStateChanged(authPhoneState: AuthPhoneInterceptor.AuthPhoneState) {
        when (authPhoneState) {
            AuthPhoneInterceptor.AuthPhoneState.STATE_CODE_SENT -> hideProgress()
            AuthPhoneInterceptor.AuthPhoneState.STATE_INITIALIZED -> {
                hideProgress()
                showInitFragmentLayout()
            }
            AuthPhoneInterceptor.AuthPhoneState.STATE_SIGNIN_FAILED -> hideProgress()
            AuthPhoneInterceptor.AuthPhoneState.STATE_SIGNIN_SUCCESS -> {
                hideProgress()
                authPhoneSignInSuccess()
            }
            AuthPhoneInterceptor.AuthPhoneState.STATE_VERIFY_FAILED -> hideProgress()
            AuthPhoneInterceptor.AuthPhoneState.STATE_VERIFY_SUCCESS -> hideProgress()
        }
    }

    private fun showInitFragmentLayout() {
        mFragment?.codeReceivedLayoutVisibility(View.GONE)
        mFragment?.changeButtonText("GET")
        mFragment?.singOutButtonVisibility(View.GONE)
        Toast.makeText(this, getString(R.string.sign_out_success), Toast.LENGTH_LONG).show()
    }

    private fun authPhoneSignInSuccess() {
        mFragment?.singOutButtonVisibility(View.VISIBLE)
        Toast.makeText(this, getString(R.string.sign_in_success), Toast.LENGTH_LONG).show()
    }

    override fun authPhoneError(authPhoneError: AuthPhoneInterceptor.AuthPhoneError) {
        hideProgress()
        when (authPhoneError) {
            AuthPhoneInterceptor.AuthPhoneError.INSTANT_VALIDATION -> {
            }
            AuthPhoneInterceptor.AuthPhoneError.INVALID_CODE -> showError(0, getString(R.string.unespected_error_title), getString(R.string.invalid_code))
            AuthPhoneInterceptor.AuthPhoneError.INVALID_PHONE_NUMBER -> showError(0, getString(R.string.unespected_error_title), getString(R.string.invalid_phone))
            AuthPhoneInterceptor.AuthPhoneError.QUOTA_EXCEEDED -> showError(0, getString(R.string.unespected_error_title), getString(R.string.quota_exceeded))
        }
    }

    override fun signOut() {
        showProgress(0f, "Loading")
        mAuthPhoneInterceptor.signOut()
    }
}

