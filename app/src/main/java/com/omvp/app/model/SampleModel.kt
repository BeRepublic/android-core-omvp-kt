package com.omvp.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SampleModel(val title: String, val link: String, val pubdate:String) : Parcelable
