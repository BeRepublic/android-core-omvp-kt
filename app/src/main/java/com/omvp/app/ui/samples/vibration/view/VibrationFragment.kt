package com.omvp.app.ui.samples.vibration.view

import android.os.Bundle
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.vibration.presenter.VibrationPresenterImpl

class VibrationFragment : BaseViewFragment<VibrationPresenterImpl,
        VibrationFragment.FragmentCallback>(), VibrationView {

//    internal lateinit var mSeekBar: SeekBar
//    internal lateinit var mDurationTextView: AppCompatTextView

    private var mDuration: Long = 100

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onVibrateSelected(duration: Long)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
/*        mSeekBar.progress = 1

        mSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateDuration(progress)
                if (progress < 1) {
                    seekBar.progress = 1
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })*/
    }

    fun onVibrateClicked() {
        mCallback.onVibrateSelected(mDuration)
    }

    private fun updateDuration(progress: Int) {
        mDuration = (progress * 100).toLong()
//        mDurationTextView.text = mDuration.toString() + " ms"
    }

    companion object {

        fun newInstance(bundle: Bundle?) = VibrationFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
