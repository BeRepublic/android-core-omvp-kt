package com.omvp.app.base.ui

import android.support.v7.util.DiffUtil

abstract class BaseDiffUtilsCallback<T>(protected var newList: List<T>?, protected var oldList: List<T>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    /*
        Here, we don’t have any key parameter to check if the items are same.
        So, we are always returning true which invokes areContentTheSame() for every position.
    */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}
