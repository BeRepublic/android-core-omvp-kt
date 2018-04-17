package com.omvp.app.model

import android.os.Parcelable
import com.omvp.app.R
import com.omvp.app.base.ui.BaseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SampleModel(var title: String, val link: String, val pubdate: String, val imageResId: Int) : BaseModel(), Parcelable {

    constructor(): this("", "", "", R.mipmap.ic_launcher_round)

    override fun compareTo(other: Any): Int {
        val (title1, link1, pubdate1, imageResId1) = other as SampleModel
        return if (title == title1
                && link == link1
                && pubdate == pubdate1
                && imageResId == imageResId1) {
            0
        } else 1
    }

}
