package com.omvp.data.network.filter

import android.content.Context

import com.omvp.commons.AppException
import com.omvp.data.R

import org.json.JSONArray

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Predicate

class JSONArrayErrorFilter(private val context: Context) : Predicate<JSONArray> {
    override fun test(t: JSONArray): Boolean {
        return true
    }
}
