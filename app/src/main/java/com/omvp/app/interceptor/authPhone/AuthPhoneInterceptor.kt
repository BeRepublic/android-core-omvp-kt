package com.omvp.app.interceptor.authPhone

import com.raxdenstudios.square.interceptor.Interceptor

interface AuthPhoneInterceptor : Interceptor {

    enum class AuthPhoneState {
        STATE_INITIALIZED, STATE_CODE_SENT, STATE_VERIFY_FAILED, STATE_VERIFY_SUCCESS, STATE_SIGNIN_FAILED, STATE_SIGNIN_SUCCESS
    }

    enum class AuthPhoneError {
        INVALID_PHONE_NUMBER, QUOTA_EXCEEDED, INVALID_CODE, INSTANT_VALIDATION
    }

    fun startPhoneNumberVerification(phoneNumber: String)

    fun signOut()

    fun verificationCode(code: String)

    fun resendVerificationCode()
}
