package com.omvp.app.interceptor.permission

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.omvp.app.helper.DialogHelper
import com.omvp.app.util.PermissionUtil
import com.raxdenstudios.square.interceptor.ActivityInterceptor


class PermissionActivityInterceptor(activity: FragmentActivity, callback: PermissionInterceptorCallback) : ActivityInterceptor<PermissionInterceptorCallback>(activity, callback),
        PermissionInterceptor {

    private val mDialogHelper: DialogHelper = DialogHelper(mActivity, activity.supportFragmentManager)
    private var mPermission: Permission? = null

    enum class Permission {
        NONE, LOCATION, CAMERA, READ_EXTERNAL_STORAGE, RECORD_AUDIO
    }

    init {
        mActivity = activity
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val permission = Permission.values()[requestCode]
        var permissionGranted = false
        var permissionDeniedForEver = false
        when (permission) {
            PermissionActivityInterceptor.Permission.LOCATION -> {
                permissionGranted = isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)
                permissionDeniedForEver = !PermissionUtil.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            }
            PermissionActivityInterceptor.Permission.CAMERA -> {
                permissionGranted = isPermissionGranted(permissions, grantResults, Manifest.permission.CAMERA)
                permissionDeniedForEver = !PermissionUtil.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)
            }
            PermissionActivityInterceptor.Permission.READ_EXTERNAL_STORAGE -> {
                permissionGranted = isPermissionGranted(permissions, grantResults, Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionDeniedForEver = !PermissionUtil.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            PermissionActivityInterceptor.Permission.RECORD_AUDIO -> {
                permissionGranted = isPermissionGranted(permissions, grantResults, Manifest.permission.RECORD_AUDIO)
                permissionDeniedForEver = !PermissionUtil.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO)
            }
            else -> {
                //
            }
        }
        if (permissionGranted) {
            permissionGranted()
        } else {
            if (permissionDeniedForEver) {
                permissionDeniedForEver()
            } else {
                permissionDenied()
            }
        }
    }

    override fun requestPermission(permission: Permission) {
        mPermission = permission

        val manifestPerm: String = when (mPermission) {
            PermissionActivityInterceptor.Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
            PermissionActivityInterceptor.Permission.CAMERA -> Manifest.permission.CAMERA
            PermissionActivityInterceptor.Permission.READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
            PermissionActivityInterceptor.Permission.RECORD_AUDIO -> Manifest.permission.RECORD_AUDIO
            else -> return
        }
        if (PermissionUtil.checkSelfPermission(mActivity, manifestPerm)) {
            if (PermissionUtil.shouldShowRequestPermissionRationale(mActivity, manifestPerm)) {
                showRationalePermissionDialog()
            } else {
                requestPermissions()
            }
        } else {
            permissionAlreadyGranted()
        }
    }

    override fun hasPermission(permission: Permission): Boolean {
        val manifestPerm: String = when (permission) {
            PermissionActivityInterceptor.Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
            PermissionActivityInterceptor.Permission.CAMERA -> Manifest.permission.CAMERA
            PermissionActivityInterceptor.Permission.READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
            PermissionActivityInterceptor.Permission.RECORD_AUDIO -> Manifest.permission.RECORD_AUDIO
            else -> return false
        }
        return !PermissionUtil.checkSelfPermission(mActivity, manifestPerm)
    }

    private fun requestPermissions() {
        when (mPermission) {
            PermissionActivityInterceptor.Permission.LOCATION -> PermissionUtil.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    mPermission!!.ordinal)
            PermissionActivityInterceptor.Permission.CAMERA -> PermissionUtil.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    mPermission!!.ordinal)
            PermissionActivityInterceptor.Permission.READ_EXTERNAL_STORAGE -> {
                PermissionUtil.requestPermissions(mActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        mPermission!!.ordinal)
                PermissionUtil.requestPermissions(mActivity,
                        arrayOf(Manifest.permission.RECORD_AUDIO),
                        mPermission!!.ordinal)
            }
            PermissionActivityInterceptor.Permission.RECORD_AUDIO -> PermissionUtil.requestPermissions(mActivity, arrayOf(Manifest.permission.RECORD_AUDIO), mPermission!!.ordinal)
            else -> {
                //
            }
        }
    }

    private fun showRationalePermissionDialog() {
        //TODO SHOW PERMISSIONS DIALOG
        when (mPermission) {
            PermissionActivityInterceptor.Permission.LOCATION -> Toast.makeText(mContext, "Actualmente tienes el GPS desactivado, activalo para poder obtener resultados de calidad", Toast.LENGTH_LONG).show()
            PermissionActivityInterceptor.Permission.CAMERA -> Toast.makeText(mContext, "Danos permisos para poder acceder a la camara y a tus fotos", Toast.LENGTH_LONG).show()
            PermissionActivityInterceptor.Permission.READ_EXTERNAL_STORAGE -> Toast.makeText(mContext, "Danos permisos para poder acceder a la memoria del teléfono", Toast.LENGTH_LONG).show()
            PermissionActivityInterceptor.Permission.RECORD_AUDIO -> Toast.makeText(mContext, "Para que puedas realizar un pedido con tu voz necesitamos poder acceder al microfono.", Toast.LENGTH_LONG).show()
            else -> {
                //
            }
        }
    }

    private fun isPermissionGranted(grantPerms: Array<String>, grantResults: IntArray, perms: String): Boolean {
        for (i in grantPerms.indices) {
            if (perms == grantPerms[i]) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    private fun permissionDenied() {
        if (mCallback != null) {
            mCallback.onPermissionDenied(mPermission!!)
        }
    }

    private fun permissionDeniedForEver() {
        if (mCallback != null) {
            mCallback.onPermissionDeniedForEver(mPermission!!)
        }
    }

    private fun permissionAlreadyGranted() {
        if (mCallback != null) {
            mCallback.onPermissionAlreadyGranted(mPermission!!)
        }
    }

    private fun permissionGranted() {
        if (mCallback != null) {
            mCallback.onPermissionGranted(mPermission!!)
        }
    }

}
