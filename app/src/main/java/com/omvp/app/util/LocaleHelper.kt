package com.omvp.app.util

import android.content.res.Configuration
import android.content.res.Resources

import java.util.Locale

/**
 * Created by Angel on 26/03/2018.
 */

object LocaleHelper {

    @JvmStatic
    fun updateConfiguration(resources: Resources, locale: Locale) {
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}
