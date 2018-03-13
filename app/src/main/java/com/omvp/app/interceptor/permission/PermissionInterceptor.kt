package com.omvp.app.interceptor.permission

import com.raxdenstudios.square.interceptor.Interceptor

interface PermissionInterceptor : Interceptor {

    fun requestPermission(permission: PermissionActivityInterceptor.Permission)

    fun hasPermission(permission: PermissionActivityInterceptor.Permission): Boolean
}
