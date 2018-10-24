package com.omvp.app.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

object PermissionUtil {

    @JvmStatic
    fun checkSelfPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
    }

    @JvmStatic
    fun shouldShowRequestPermissionRationale(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    @JvmStatic
    fun requestPermissions(activity: Activity, permissions: Array<String>, constantPermission: Int) {
        ActivityCompat.requestPermissions(activity, permissions, constantPermission)
    }

}
