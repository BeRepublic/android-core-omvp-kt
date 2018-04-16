package com.omvp.app.interceptor.authPhone

import com.google.firebase.auth.FirebaseUser
import com.raxdenstudios.square.interceptor.InterceptorCallback

interface AuthPhoneInterceptorCallback : InterceptorCallback {

    fun authPhoneUserRetrieved(firebaseUser: FirebaseUser)

    fun authPhoneCodeRetrieved(code: String)

    fun authPhoneStateChanged(authPhoneState: AuthPhoneInterceptor.AuthPhoneState)

    fun authPhoneError(authPhoneError: AuthPhoneInterceptor.AuthPhoneError)

}
