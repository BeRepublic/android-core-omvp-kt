package com.omvp.app.interceptor.takePicture

import android.net.Uri

import com.raxdenstudios.square.interceptor.InterceptorCallback

interface TakePictureInterceptorCallback : InterceptorCallback {

    fun onWorkingPictureProgress(workingProgress: Boolean)

    fun onPictureRetrieved(bitmap: Uri)

}
