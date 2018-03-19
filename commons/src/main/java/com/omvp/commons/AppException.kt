package com.omvp.commons

/**
 * Created by Angel on 15/02/2018.
 */

class AppException : RuntimeException {

    var code: Int = 0

    constructor(detailMessage: String) : super(detailMessage)

    constructor(code: Int, detailMessage: String) : super(detailMessage) {
        this.code = code
    }

}
