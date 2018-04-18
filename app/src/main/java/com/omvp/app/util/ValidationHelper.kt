package com.omvp.app.util

import android.util.Patterns

import com.omvp.commons.Constants
import com.raxdenstudios.commons.util.Utils

import java.util.regex.Pattern

object ValidationHelper {

    fun validateEmail(email: String): Boolean {
        return Utils.hasValue(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        val pattern = Pattern.compile(Constants.PASSWORD_PATTERN)
        return Utils.hasValue(password) && pattern.matcher(password).matches()
    }

    fun validatePhone(phone: String): Boolean {
        return Utils.hasValue(phone) && Patterns.PHONE.matcher(phone).matches()
    }

    fun validateUrl(personalUrl: String): Boolean {
        return Patterns.WEB_URL.matcher(personalUrl).matches()
    }
}
