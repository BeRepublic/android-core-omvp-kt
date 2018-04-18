package com.omvp.app.ui.samples.takepicture.view

import android.net.Uri
import com.omvp.app.base.mvp.view.BaseView

/**
 * Created by Angel on 21/02/2018.
 */
interface SampleTakePictureView : BaseView {
    fun pictureRetrieved(picture: Uri)
}
