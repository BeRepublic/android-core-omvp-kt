package com.omvp.app.ui.samples.sampletakepicture.view

import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.sampletakepicture.presenter.SampleTakePicturePresenter
import com.omvp.app.util.ImageHelper

import butterknife.BindView
import butterknife.OnClick

class SampleTakePictureFragment : BaseViewFragment<SampleTakePicturePresenter,
        SampleTakePictureFragment.FragmentCallback>(),
        SampleTakePictureView {


    @BindView(R.id.take_picture_image)
    internal lateinit var mTakePictureImg: AppCompatImageView

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onGalleryImageRequested()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

    }

    @OnClick(R.id.take_picture_image)
    fun onTakePictureImagePressed(view: View) {
        mCallback.onGalleryImageRequested()
    }

    fun pictureRetrieved(uri: Uri) {
        ImageHelper.loadImageUser(activity, uri, mTakePictureImg, null)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = SampleTakePictureFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
