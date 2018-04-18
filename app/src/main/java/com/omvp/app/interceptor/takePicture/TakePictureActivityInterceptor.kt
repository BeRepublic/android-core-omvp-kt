package com.omvp.app.interceptor.takePicture

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import com.omvp.app.R
import com.omvp.app.base.reactivex.BaseDisposableSingleObserver
import com.omvp.app.util.ImagePickerUtil
import com.raxdenstudios.square.interceptor.ActivitySimpleInterceptor
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


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

class TakePictureActivityInterceptor(activity: Activity) : ActivitySimpleInterceptor(activity),
        TakePictureInterceptor {

    private var mRxPermissions: RxPermissions = RxPermissions(activity)
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mListener: TakePictureListener? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            retrieveImageFromResult(requestCode, resultCode, data)
        } else {
            mListener?.onWorkingPictureProgress(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun takePicture(chooserTitle: String, listener: TakePictureListener) {
        mCompositeDisposable.add(mRxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Permission>() {
                    override fun onStart() {
                        mListener = listener
                        mListener?.onWorkingPictureProgress(true)
                    }

                    override fun onNext(permission: Permission) {
                        handlePermissionResult(permission, chooserTitle)
                    }

                    override fun onError(e: Throwable) {
                        mListener?.onWorkingPictureProgress(false)
                        Timber.e(e)
                    }

                    override fun onComplete() {}

                }))
    }

    private fun handlePermissionResult(permission: Permission, chooserTitle: String) {
        if (permission.granted) {
            mCompositeDisposable.add(processTakePictureIntent(chooserTitle)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Intent>() {
                        override fun onSuccess(intent: Intent) {
                            mListener?.onWorkingPictureProgress(false)
                            launchActivityForResult(intent)
                        }

                        override fun onError(e: Throwable) {
                            mListener?.onWorkingPictureProgress(false)
                            Timber.e(e)
                        }
                    }))
        } else if (permission.shouldShowRequestPermissionRationale) {
            AlertDialog.Builder(mActivity)
                    .setTitle(R.string.camera_permission_title)
                    .setMessage(R.string.camera_permission_description)
                    .setPositiveButton(R.string.camera_permission_positive_button, null)
                    .create()
                    .show()
        }
    }

    private fun retrieveImageFromResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCompositeDisposable.add(processImageFromResult(requestCode, resultCode, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : BaseDisposableSingleObserver<Uri>(mActivity) {
                    override fun onError(code: Int, title: String?, description: String?) {
                        mListener?.onWorkingPictureProgress(false)
                    }

                    override fun onSuccess(@NonNull uri: Uri) {
                        mListener?.onWorkingPictureProgress(false)
                        mListener?.onPictureRetrieved(uri)
                    }
                }))
    }

    private fun launchActivityForResult(intent: Intent) {
        if (intent.resolveActivity(mContext.packageManager) != null) {
            (mContext as Activity).startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun processImageFromResult(requestCode: Int, resultCode: Int, data: Intent?): Single<Uri> {
        return Single.create { emitter ->
            try {
                if (!emitter.isDisposed) {
                    val uri = ImagePickerUtil.getUriFromResult(mContext, resultCode, data)
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
