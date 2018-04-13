package com.omvp.app.interceptor.authPhone;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.raxdenstudios.square.interceptor.ActivityInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class AuthPhoneActivityInterceptor extends ActivityInterceptor<AuthPhoneInterceptorCallback> implements AuthPhoneInterceptor {

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private AuthPhoneState mAuthPhoneState;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential mPhoneAuthCredential;
    private boolean mVerificationInProgress = false;
    private String mPhoneNumber;
    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public AuthPhoneActivityInterceptor(Activity activity, AuthPhoneInterceptorCallback callback) {
        super(activity, callback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        retrieveFirebaseUser();
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification();
        }
        initializePhoneAuthCallbacks();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    public void startPhoneNumberVerification(String phoneNumber) {
        mPhoneNumber = phoneNumber;
        startPhoneNumberVerification();
    }

    @Override
    public void signOut() {
        mAuth.signOut();
        authPhoneStateChanged(AuthPhoneState.STATE_INITIALIZED);
    }

    @Override
    public void verificationCode(String code) {
        verifyPhoneNumberWithCode(mVerificationId, code);
    }

    @Override
    public void resendVerificationCode() {
        resendVerificationCode(mPhoneNumber, mResendToken);
    }

    private void authPhoneCredentialRetrieved(PhoneAuthCredential credential) {
        if (credential != null) {
            mPhoneAuthCredential = credential;
            if (!TextUtils.isEmpty(credential.getSmsCode())) {
                mCallback.authPhoneCodeRetrieved(credential.getSmsCode());
            } else {
                mCallback.authPhoneError(AuthPhoneError.INSTANT_VALIDATION);
            }
        }
    }

    private void initializePhoneAuthCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Timber.d("onVerificationCompleted: %s", credential);
                mVerificationInProgress = false;
                // Update the UI and attempt sign in with the phone credential
                authPhoneStateChanged(AuthPhoneState.STATE_VERIFY_SUCCESS);
                authPhoneCredentialRetrieved(credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Timber.e(e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    mCallback.authPhoneError(AuthPhoneError.INVALID_PHONE_NUMBER);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    mCallback.authPhoneError(AuthPhoneError.QUOTA_EXCEEDED);
                }
                // Show a message and update the UI
                authPhoneStateChanged(AuthPhoneState.STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Timber.d("onCodeSent: %s", verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                authPhoneStateChanged(AuthPhoneState.STATE_CODE_SENT);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.d("signInWithCredential:success");
                            handleFirebaseUser(task.getResult().getUser());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Timber.e(task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                mCallback.authPhoneError(AuthPhoneError.INVALID_CODE);
                            }
                            authPhoneStateChanged(AuthPhoneState.STATE_SIGNIN_FAILED);
                        }
                    }
                });
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,       // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,          // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,          // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void retrieveFirebaseUser() {
        handleFirebaseUser(mAuth.getCurrentUser());
    }

    private void handleFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            authPhoneStateChanged(AuthPhoneState.STATE_SIGNIN_SUCCESS);
            mCallback.authPhoneUserRetrieved(firebaseUser);
        } else {
            authPhoneStateChanged(AuthPhoneState.STATE_INITIALIZED);
        }
    }

    private void authPhoneStateChanged(AuthPhoneState authPhoneState) {
        mAuthPhoneState = authPhoneState;
        mCallback.authPhoneStateChanged(authPhoneState);
    }

    private boolean validatePhoneNumber() {
        if (TextUtils.isEmpty(mPhoneNumber)) {
            mCallback.authPhoneError(AuthPhoneError.INVALID_PHONE_NUMBER);
            return false;
        }
        return true;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

}
