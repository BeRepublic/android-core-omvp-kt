package com.omvp.app.injector.module

import android.content.Context

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.omvp.app.R

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class GoogleModule {

    @Provides
    @Singleton
    internal fun provideGoogleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }

}
