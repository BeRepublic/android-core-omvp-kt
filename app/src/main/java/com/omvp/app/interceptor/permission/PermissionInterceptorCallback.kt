package com.omvp.app.interceptor.permission

import com.raxdenstudios.square.interceptor.InterceptorCallback

interface PermissionInterceptorCallback : InterceptorCallback {

    fun onPermissionGranted(permission: PermissionActivityInterceptor.Permission)

    fun onPermissionAlreadyGranted(permission: PermissionActivityInterceptor.Permission)

    fun onPermissionDenied(permission: PermissionActivityInterceptor.Permission)

    fun onPermissionDeniedForEver(permission: PermissionActivityInterceptor.Permission)
}
