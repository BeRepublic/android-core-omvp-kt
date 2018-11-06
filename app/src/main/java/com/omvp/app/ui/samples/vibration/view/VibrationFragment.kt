package com.omvp.app.ui.samples.vibration.view

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.vibration.presenter.VibrationPresenterImpl
import kotlinx.android.synthetic.main.vibration_fragment.*

class VibrationFragment : BaseViewFragment<VibrationPresenterImpl,
        VibrationFragment.FragmentCallback>(), VibrationView {

    private var mDuration: Long = 100

    interface FragmentCallback : BaseViewFragmentCallback {
        fun onVibrateSelected(duration: Long)
    }

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)
        setupViews()
    }

    private fun setupViews() {
        button1.setOnClickListener { mCallback.onVibrateSelected(mDuration) }
        seekbar.progress = 1

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
        })
    }

    private fun updateDuration(progress: Int) {
        mDuration = (progress * 100).toLong()
        duration.text = "$mDuration ms"
    }

    companion object {

        fun newInstance(bundle: Bundle?) = VibrationFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}
