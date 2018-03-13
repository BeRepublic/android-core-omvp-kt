package com.omvp.app.interceptor.takePicture

import android.app.Activity
import android.content.Intent
import android.net.Uri

import com.omvp.app.base.reactivex.BaseDisposableSingleObserver
import com.omvp.app.util.ImagePickerUtil
import com.raxdenstudios.square.interceptor.ActivityInterceptor

import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Remember to add to the AndroidManifest.xml the FileProvider info into Application tag
 * <application ...>
 *
 * <provider android:name="android.support.v4.content.FileProvider" android:authorities="${applicationId}.fileprovider" android:exported="false" android:grantUriPermissions="true">
 * <meta-data android:name="android.support.FILE_PROVIDER_PATHS" android:resource="@xml/provider_paths"></meta-data></provider>
 *
 *</application>
 *
 * And remember to add the provider_paths.xml into a xml file and stored into res/xml
 *
 * <paths xmlns:android="http://schemas.android.com/apk/res/android">
 * <external-path name="external_files" path="."></external-path></paths>*
 *
 */

class TakePictureActivityInterceptor(activity: Activity, callback: TakePictureInterceptorCallback) :
        ActivityInterceptor<TakePictureInterceptorCallback>(activity, callback),
        TakePictureInterceptor {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            retrieveImageFromResult(requestCode, resultCode, data)
        } else {
            workingPictureProgress(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun takePicture(chooserTitle: String) {
        val d = processTakePictureIntent(chooserTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableSingleObserver<Intent>(mActivity) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        workingPictureProgress(false)
                    }

                    override fun onStart() {
                        workingPictureProgress(true)
                    }

                    override fun onSuccess(@NonNull intent: Intent) {
                        launchActivityForResult(intent)
                    }
                })
        mCompositeDisposable.add(d)
    }

    private fun retrieveImageFromResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val d = processImageFromResult(requestCode, resultCode, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableSingleObserver<Uri>(mActivity) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        workingPictureProgress(false)
                    }

                    override fun onSuccess(@NonNull uri: Uri) {
                        workingPictureProgress(false)
                        pictureRetrieved(uri)
                    }
                })
        mCompositeDisposable.add(d)
    }

    private fun launchActivityForResult(intent: Intent) {
        if (intent.resolveActivity(mContext.packageManager) != null) {
            (mContext as Activity).startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun pictureRetrieved(uri: Uri) {
        if (mCallback != null) {
            mCallback.onPictureRetrieved(uri)
        }
    }

    private fun workingPictureProgress(workingProgress: Boolean) {
        if (mCallback != null) {
            mCallback.onWorkingPictureProgress(workingProgress)
        }
    }

    private fun processImageFromResult(requestCode: Int, resultCode: Int, data: Intent?): Single<Uri> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    val uri = data?.let { ImagePickerUtil.getUriFromResult(mContext, resultCode, it) }
                    if (uri != null) {
                        emitter.onSuccess(uri)
                    }
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    private fun processTakePictureIntent(chooserTitle: String): Single<Intent> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    val intent = ImagePickerUtil.getPickImageIntent(mContext, chooserTitle)
                    if (intent != null) {
                        emitter.onSuccess(intent)
                    }
                }
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    companion object {

        private const val REQUEST_IMAGE_CAPTURE = 10023
    }


}
