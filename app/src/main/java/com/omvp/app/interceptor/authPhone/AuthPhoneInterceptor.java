package com.omvp.app.interceptor.authPhone;

import com.raxdenstudios.square.interceptor.Interceptor;

public interface AuthPhoneInterceptor extends Interceptor {

    enum AuthPhoneState {STATE_INITIALIZED, STATE_CODE_SENT, STATE_VERIFY_FAILED, STATE_VERIFY_SUCCESS, STATE_SIGNIN_FAILED, STATE_SIGNIN_SUCCESS}

    enum AuthPhoneError {INVALID_PHONE_NUMBER, QUOTA_EXCEEDED, INVALID_CODE, INSTANT_VALIDATION}

    void startPhoneNumberVerification(String phoneNumber);

    void signOut();

    void verificationCode(String code);

    void resendVerificationCode();

    String getPhoneNumber();

}
