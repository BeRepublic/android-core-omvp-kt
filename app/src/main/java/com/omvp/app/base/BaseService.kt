package com.omvp.app.base

import android.app.Service

import com.omvp.app.util.DisposableManager

import javax.inject.Inject
import javax.inject.Named

import dagger.android.AndroidInjection

import com.omvp.app.base.BaseServiceModule.DISPOSABLE_SERVICE_MANAGER

abstract class BaseService : Service() {

    @Inject
    @Named(DISPOSABLE_SERVICE_MANAGER)
    protected lateinit var mDisposableManager: DisposableManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        mDisposableManager.dispose()
    }

}
