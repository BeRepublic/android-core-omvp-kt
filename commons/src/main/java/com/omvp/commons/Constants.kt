package com.omvp.commons

/**
 * Created by Angel on 18/07/2017.
 */

object Constants {

    const val SPLASH_DELAY = 1500

    //LOCATION
    const val LOCATION_INTERVAL = 100L
    const val LOCATION_FASTEST_INTERVAL: Long = LOCATION_INTERVAL / 2

    // Patterns
    const val PASSWORD_PATTERN = "^[0-9A-Za-z!@#$.%_]{6,}$"

    // Request Codes
    const val REQUEST_CODE_REFRESH = 1001
}
