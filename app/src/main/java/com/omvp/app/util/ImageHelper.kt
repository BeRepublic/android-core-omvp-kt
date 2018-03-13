package com.omvp.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object ImageHelper {

    enum class ScaleType {
        NONE, CENTER_CROP, FIT_CENTER, CIRCLE_CROP
    }

    fun loadImageUser(context: Context, imageUser: Uri, imageView: ImageView, progressView: View?) {
        val requestBuilder = Glide.with(context)
                .load(imageUser)
                .transition(DrawableTransitionOptions.withCrossFade())
        loadTransformations(context, requestBuilder, 0, 0, 0, ScaleType.CENTER_CROP, 0, null, 0)
        if (progressView != null) {
            loadProgressView(requestBuilder, progressView)
        }
        requestBuilder.into(imageView)
    }

    fun loadImageUser(context: Context, imageUser: String, imageView: ImageView, progressView: View?) {
        val requestBuilder = Glide.with(context)
                .load(imageUser)
                .transition(DrawableTransitionOptions.withCrossFade())
        loadTransformations(context, requestBuilder, 0, 0, 0, ScaleType.CIRCLE_CROP, 0, null, 0)
        if (progressView != null) {
            loadProgressView(requestBuilder, progressView)
        }
        requestBuilder.into(imageView)
    }

    fun loadImageUser(context: Context, imageUser: Bitmap, imageView: ImageView, progressView: View?) {
        val requestBuilder = Glide.with(context)
                .load(imageUser)
                .transition(DrawableTransitionOptions.withCrossFade())
        loadTransformations(context, requestBuilder, 0, 0, 0, ScaleType.CIRCLE_CROP, 0, null, 0)
        if (progressView != null) {
            loadProgressView(requestBuilder, progressView)
        }
        requestBuilder.into(imageView)
    }

    @SuppressLint("CheckResult")
    private fun loadTransformations(context: Context, requestBuilder: RequestBuilder<Drawable>,
                                    width: Int, height: Int, holderId: Int, scaleType: ScaleType,
                                    cornerRadius: Int, cornerType: RoundedCornersTransformation.CornerType?, blurRadius: Int) {
        var multiTransformation: MultiTransformation<Bitmap>? = null
        when (scaleType) {
            ImageHelper.ScaleType.CENTER_CROP -> multiTransformation = if (cornerRadius > 0) {
                MultiTransformation(CenterCrop(), RoundedCornersTransformation(cornerType, cornerRadius))
            } else {
                MultiTransformation(CenterCrop())
            }
            ImageHelper.ScaleType.FIT_CENTER -> multiTransformation = if (cornerRadius > 0) {
                MultiTransformation(FitCenter(), RoundedCornersTransformation(cornerType, cornerRadius))
            } else {
                MultiTransformation(FitCenter())
            }
            ImageHelper.ScaleType.CIRCLE_CROP -> multiTransformation = if (cornerRadius > 0) {
                MultiTransformation(CircleCrop(), RoundedCornersTransformation(cornerType, cornerRadius))
            } else {
                MultiTransformation(CircleCrop())
            }
            ImageHelper.ScaleType.NONE -> if (cornerRadius > 0) {
                multiTransformation = MultiTransformation(RoundedCornersTransformation(cornerType, cornerRadius))
            }
        }
        val requestOptions = RequestOptions()
        if (width > 0 && height > 0) {
            requestOptions.override(width, height)
        }
        requestOptions.priority(Priority.IMMEDIATE)
        if (holderId != 0) {
            requestOptions.placeholder(holderId)
        }
        if (multiTransformation != null) {
            requestOptions.transform(multiTransformation)
        }
        requestBuilder.apply(requestOptions)
    }

    @SuppressLint("CheckResult")
    private fun loadProgressView(requestBuilder: RequestBuilder<Drawable>, progressView: View) {
        requestBuilder.listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                progressView.visibility = View.GONE
                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                progressView.visibility = View.GONE
                return false
            }
        })
    }

}
