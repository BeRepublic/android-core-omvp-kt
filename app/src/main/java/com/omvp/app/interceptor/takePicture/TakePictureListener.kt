package com.omvp.app.interceptor.takePicture

import android.net.Uri

import com.raxdenstudios.square.interceptor.InterceptorCallback

interface TakePictureListener : InterceptorCallback {

    fun onWorkingPictureProgress(workingProgress: Boolean)

    fun onPictureRetrieved(picture: Uri)

}
