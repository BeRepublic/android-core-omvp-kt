package com.omvp.app.ui.samples.takepicture.view

import android.net.Uri
import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.takepicture.presenter.SampleTakePicturePresenter

class SampleTakePictureFragment : BaseViewFragment<SampleTakePicturePresenter,
        SampleTakePictureFragment.FragmentCallback>(),
        SampleTakePictureView {


//    internal lateinit var mTakePictureImg: AppCompatImageView

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    fun onTakePictureImagePressed() {
        mPresenter.takePictureImage()
    }

    override fun pictureRetrieved(picture: Uri) {
//        ImageHelper.loadImageUser(activity, picture, mTakePictureImg, null)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleTakePictureFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
