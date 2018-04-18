package com.omvp.app.ui.samples.vibration

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.widget.Toolbar
import android.view.View

import com.omvp.app.R
import com.omvp.app.base.mvp.BaseFragmentActivity
import com.omvp.app.ui.samples.vibration.view.VibrationFragment
import com.raxdenstudios.square.interceptor.Interceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptor
import com.raxdenstudios.square.interceptor.commons.injectfragment.InjectFragmentInterceptorCallback
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptor
import com.raxdenstudios.square.interceptor.commons.toolbar.ToolbarInterceptorCallback

import javax.inject.Inject

class VibrationActivity : BaseFragmentActivity(),
        VibrationFragment.FragmentCallback,
        ToolbarInterceptorCallback,
        InjectFragmentInterceptorCallback<VibrationFragment> {

    @Inject
    internal lateinit var mToolbarInterceptor: ToolbarInterceptor
    @Inject
    internal lateinit var mInjectFragmentInterceptor: InjectFragmentInterceptor

    private lateinit var mToolbar: Toolbar
    private lateinit var mFragment: VibrationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViews()
    }

    // =============== ToolbarInterceptorCallback ==================================================

    override fun onCreateToolbarView(savedInstanceState: Bundle?): Toolbar {
        return findViewById(R.id.toolbar)
    }

    override fun onToolbarViewCreated(toolbar: Toolbar) {
        mToolbar = toolbar
    }

    // =============== InjectFragmentInterceptorCallback ===========================================

    override fun onLoadFragmentContainer(savedInstanceState: Bundle?): View {
        return findViewById(R.id.content)
    }

    override fun onCreateFragment(): VibrationFragment {
        return VibrationFragment.newInstance(mExtras)
    }

    override fun onFragmentLoaded(fragment: VibrationFragment) {
        mFragment = fragment
    }

    // =============== Support methods =============================================================

    override fun setupInterceptors(interceptorList: MutableList<Interceptor>) {
        super.setupInterceptors(interceptorList)
        interceptorList.add(mToolbarInterceptor)
        interceptorList.add(mInjectFragmentInterceptor)
    }

    private fun setUpViews() {

    }

    override fun onVibrateSelected(duration: Long) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                vibrator.vibrate(duration)
            }
        }
    }
}
