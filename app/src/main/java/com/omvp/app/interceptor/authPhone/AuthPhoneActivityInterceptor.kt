package com.omvp.app.interceptor.authPhone

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.raxdenstudios.square.interceptor.ActivityInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AuthPhoneActivityInterceptor(activity: FragmentActivity, callback: AuthPhoneInterceptorCallback) :
        ActivityInterceptor<AuthPhoneInterceptorCallback>(activity, callback),
        AuthPhoneInterceptor {

    private lateinit var mAuth: FirebaseAuth
    private var mAuthPhoneState: AuthPhoneInterceptor.AuthPhoneState? = null
    private var mPhoneAuthCredential: PhoneAuthCredential? = null
    private var mVerificationInProgress = false
    private var mVerificationId: String? = null
    private var phoneNumber: String = ""

    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        retrieveFirebaseUser()
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification()
        }
        initializePhoneAuthCallbacks()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress)
    }

    override fun startPhoneNumberVerification(phoneNumber: String) {
        this.phoneNumber = phoneNumber
        startPhoneNumberVerification()
    }

    override fun signOut() {
        mAuth.signOut()
        authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_INITIALIZED)
    }

    override fun verificationCode(code: String) {
        verifyPhoneNumberWithCode(mVerificationId, code)
    }

    override fun resendVerificationCode() {
        resendVerificationCode(phoneNumber, mResendToken)
    }

    private fun authPhoneCredentialRetrieved(credential: PhoneAuthCredential) {
            mPhoneAuthCredential = credential
            if (credential.smsCode?.isNotEmpty() == true) {
                mCallback.authPhoneCodeRetrieved(credential.smsCode!!)
            } else {
                mCallback.authPhoneError(AuthPhoneInterceptor.AuthPhoneError.INSTANT_VALIDATION)
            }
    }

    private fun initializePhoneAuthCallbacks() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Timber.d("onVerificationCompleted: %s", credential)
                mVerificationInProgress = false
                // Update the UI and attempt sign in with the phone credential
                authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_VERIFY_SUCCESS)
                authPhoneCredentialRetrieved(credential)
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Timber.e(e)
                mVerificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    mCallback.authPhoneError(AuthPhoneInterceptor.AuthPhoneError.INVALID_PHONE_NUMBER)
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    mCallback.authPhoneError(AuthPhoneInterceptor.AuthPhoneError.QUOTA_EXCEEDED)
                }
                // Show a message and update the UI
                authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_VERIFY_FAILED)
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Timber.d("onCodeSent: %s", verificationId)

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token

                authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_CODE_SENT)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCredential:success")
                        handleFirebaseUser(task.result.user)
                    } else {
                        // Sign in failed, display a message and update the UI
                        Timber.e(task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            mCallback.authPhoneError(AuthPhoneInterceptor.AuthPhoneError.INVALID_CODE)
                        }
                        authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_SIGNIN_FAILED)
                    }
                }
    }

    private fun startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                mActivity, // Activity (for callback binding)
                mCallbacks)        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                mActivity, // Activity (for callback binding)
                mCallbacks, // OnVerificationStateChangedCallbacks
                token)             // ForceResendingToken from callbacks
    }

    private fun retrieveFirebaseUser() {
        handleFirebaseUser(mAuth.currentUser)
    }

    private fun handleFirebaseUser(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_SIGNIN_SUCCESS)
            mCallback.authPhoneUserRetrieved(firebaseUser)
        } else {
            authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState.STATE_INITIALIZED)
        }
    }

    private fun authPhoneStateChanged(authPhoneState: AuthPhoneInterceptor.AuthPhoneState) {
        mAuthPhoneState = authPhoneState
        mCallback.authPhoneStateChanged(authPhoneState)
    }

    private fun validatePhoneNumber(): Boolean {
        if (TextUtils.isEmpty(phoneNumber)) {
            mCallback.authPhoneError(AuthPhoneInterceptor.AuthPhoneError.INVALID_PHONE_NUMBER)
            return false
        }
        return true
    }

    companion object {

        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
    }

}
