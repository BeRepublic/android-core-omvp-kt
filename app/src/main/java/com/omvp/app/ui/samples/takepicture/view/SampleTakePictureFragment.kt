package com.omvp.app.ui.samples.takepicture.view

import android.net.Uri
import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.takepicture.presenter.SampleTakePicturePresenter
import com.omvp.app.util.ImageHelper
import kotlinx.android.synthetic.main.sample_take_picture_fragment.*

class SampleTakePictureFragment : BaseViewFragment<SampleTakePicturePresenter,
        SampleTakePictureFragment.FragmentCallback>(),
        SampleTakePictureView {

    interface FragmentCallback : BaseViewFragmentCallback

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        take_picture_image.setOnClickListener { mPresenter.takePictureImage() }
    }

    override fun pictureRetrieved(picture: Uri) {
        ImageHelper.loadImageUser(context!!, picture, take_picture_image, null)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleTakePictureFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
