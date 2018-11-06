package com.omvp.app.base

import android.app.Service
import com.omvp.app.base.BaseServiceModule.Companion.DISPOSABLE_SERVICE_MANAGER
import com.omvp.app.util.DisposableManager
import dagger.android.AndroidInjection
import javax.inject.Inject
import javax.inject.Named

abstract class BaseService : Service() {

    @Inject
    @field:Named(DISPOSABLE_SERVICE_MANAGER)
    lateinit var mDisposableManager: DisposableManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposableManager.dispose()
    }

}
