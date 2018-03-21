package com.omvp.data.network.filter

import android.content.Context

import com.omvp.commons.AppException
import com.omvp.data.R

import org.json.JSONObject

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Predicate

class JSONObjectErrorFilter(private val context: Context) : Predicate<JSONObject> {
    override fun test(@NonNull jsonObject: JSONObject): Boolean {
        return true
    }
}
