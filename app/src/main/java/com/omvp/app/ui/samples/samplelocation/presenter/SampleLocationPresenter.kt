package com.omvp.app.ui.samples.samplelocation.presenter

import android.location.Location

import com.omvp.app.base.mvp.presenter.Presenter

interface SampleLocationPresenter : Presenter {

    fun locationChanged(location: Location?)
}
