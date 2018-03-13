package com.omvp.app.interceptor.takePicture

import com.raxdenstudios.square.interceptor.Interceptor

interface TakePictureInterceptor : Interceptor {

    fun takePicture(chooserTitle: String)

}
