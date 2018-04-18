package com.omvp.commons

import java.util.regex.Pattern

/**
 * Created by Angel on 18/07/2017.
 */

object Constants {

    const val SPLASH_DELAY = 1500
    const val DEFAULT_FONT = "fonts/AvenirNextLTPro-Regular.otf"

    //LOCATION
    const val LOCATION_INTERVAL = 100L
    const val LOCATION_FASTEST_INTERVAL: Long = LOCATION_INTERVAL / 2

    // Patterns
    const val PASSWORD_PATTERN = "^[0-9A-Za-z!@#$.%_]{6,}$"
}
