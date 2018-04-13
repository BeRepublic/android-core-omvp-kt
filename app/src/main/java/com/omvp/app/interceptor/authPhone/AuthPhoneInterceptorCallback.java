package com.omvp.app.interceptor.authPhone;

import com.google.firebase.auth.FirebaseUser;
import com.raxdenstudios.square.interceptor.InterceptorCallback;

public interface AuthPhoneInterceptorCallback extends InterceptorCallback {

    void authPhoneUserRetrieved(FirebaseUser firebaseUser);

    void authPhoneCodeRetrieved(String code);

    void authPhoneStateChanged(AuthPhoneInterceptor.AuthPhoneState authPhoneState);

    void authPhoneError(AuthPhoneInterceptor.AuthPhoneError authPhoneError);

}
