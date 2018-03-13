package com.omvp.app.helper

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class AnimationHelper(private val mActivity: Activity) {

    fun fadeIn(view: View) {
        if (view.visibility != View.VISIBLE) {
            val fadeInAnimation = AnimationUtils.loadAnimation(mActivity, android.R.anim.fade_in)
            view.visibility = View.VISIBLE
            view.startAnimation(fadeInAnimation)
        }
    }

    fun fadeIn(view: View, visibility: Int) {
        if (view.visibility != visibility) {
            val fadeInAnimation = AnimationUtils.loadAnimation(mActivity, android.R.anim.fade_in)
            view.visibility = visibility
            view.startAnimation(fadeInAnimation)
        }
    }

    fun fadeOut(view: View) {
        if (view.visibility != View.GONE) {
            val fadeOutAnimation = AnimationUtils.loadAnimation(mActivity, android.R.anim.fade_out)
            view.visibility = View.GONE
            view.startAnimation(fadeOutAnimation)
        }
    }

    fun fadeOut(view: View, visibility: Int) {
        if (view.visibility != visibility) {
            val fadeOutAnimation = AnimationUtils.loadAnimation(mActivity, android.R.anim.fade_out)
            view.visibility = visibility
            view.startAnimation(fadeOutAnimation)
        }
    }

    fun clearAnimation(view: View) {
        view.clearAnimation()
    }

}
