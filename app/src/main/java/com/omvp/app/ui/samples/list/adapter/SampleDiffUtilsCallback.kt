package com.omvp.app.ui.samples.list.adapter

import android.os.Bundle

import com.omvp.app.base.ui.BaseDiffUtilsCallback
import com.omvp.app.model.SampleModel

import org.parceler.Parcels

/**
 * Extend with BaseDiffUtilsCallback and override these 2 methods
 *
 * 1. areContentsTheSame() : return true if content of two item is same
 * 2. getChangePayload() : return the difference between two items
 */

class SampleDiffUtilsCallback(newList: List<SampleModel>, oldList: List<SampleModel>) : BaseDiffUtilsCallback<SampleModel>(newList, oldList) {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList!![newItemPosition]
        val oldItem = oldList!![oldItemPosition]
        return newItem == oldItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newSampleModel = newList!![newItemPosition]
        val oldSampleModel = oldList!![oldItemPosition]

        val diff = Bundle()
        if (newSampleModel != oldSampleModel) {
            diff.putParcelable("sample", Parcels.wrap(newSampleModel))
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
}
