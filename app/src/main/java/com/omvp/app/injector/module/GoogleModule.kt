package com.omvp.app.injector.module

import android.content.Context
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.omvp.app.R

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
object GoogleModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun googleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun googleApiClientBuilder(context: Context, googleSignInOptions: GoogleSignInOptions): GoogleApiClient.Builder {
        return GoogleApiClient
                .Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun googleApiClient(context: Context, googleSignInOptions: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }
}
