package com.omvp.app.ui.samples.coroutine.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.omvp.app.base.mvp.view.BaseViewFragment
import com.omvp.app.base.mvp.view.BaseViewFragmentCallback
import com.omvp.app.ui.samples.coroutine.presenter.CoroutinePresenter
import kotlinx.android.synthetic.main.coroutine_fragment.*
import kotlinx.coroutines.*

@SuppressLint("SetTextI18n")
class CoroutineFragment : BaseViewFragment<CoroutinePresenter, CoroutineFragment.FragmentCallback>(), CoroutineView {

    interface FragmentCallback : BaseViewFragmentCallback

    companion object {

        fun newInstance(bundle: Bundle?) = CoroutineFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }

    override fun onViewLoaded(savedInstanceState: Bundle?, view: View) {
        super.onViewLoaded(savedInstanceState, view)

        button_1.setOnClickListener { launchUICoroutine() }
        button_2.setOnClickListener { launchExample2() }
    }

    private fun launchUICoroutine() = runBlocking {
        GlobalScope.launch(Dispatchers.Main) {
            button_1.isEnabled = false
            for (i in 10 downTo 1) {
                text_1.text = "Countdown $i ..."
                delay(1000L)
            }
        }
        text_1.text = "Done!"
        button_1.isEnabled = true
        delay(2000L)
    }

    private fun launchExample2() = runBlocking(Dispatchers.Main) {
        // this: CoroutineScope
        launch(Dispatchers.Main) {
            delay(200L)
            text_2.text = "Task from runBlocking"
        }

/*
        coroutineScope {
            // Creates a new coroutine scope
            launch(Dispatchers.Main) {
                delay(500L)
                text_2.text = "Task from nested launch"
            }

            delay(100L)
            text_2.text = "Task from coroutine scope" // This line will be printed before nested launch
        }
*/

        text_2.text = "Coroutine scope is over" // This line is not printed until nested launch completes
    }
}
