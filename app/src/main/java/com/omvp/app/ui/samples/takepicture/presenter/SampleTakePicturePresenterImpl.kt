package com.omvp.app.ui.samples.takepicture.presenter


import android.net.Uri
import com.omvp.app.base.mvp.presenter.BasePresenter
import com.omvp.app.interceptor.takePicture.TakePictureInterceptor
import com.omvp.app.interceptor.takePicture.TakePictureListener
import com.omvp.app.ui.samples.takepicture.view.SampleTakePictureView

import javax.inject.Inject

class SampleTakePicturePresenterImpl
@Inject
internal constructor(sampleView: SampleTakePictureView) : BasePresenter<SampleTakePictureView>(sampleView),
        SampleTakePicturePresenter {

    @Inject
    internal lateinit var mTakePictureInterceptor: TakePictureInterceptor

    override fun takePictureImage() {
        mTakePictureInterceptor.takePicture("Elige una foto", object: TakePictureListener {
            override fun onWorkingPictureProgress(workingProgress: Boolean) {
                if (workingProgress) {
                    showProgress(0f, "")
                } else {
                    hideProgress()
                }
            }

            override fun onPictureRetrieved(picture: Uri) {
                mView?.pictureRetrieved(picture)
            }
        })
    }

}
