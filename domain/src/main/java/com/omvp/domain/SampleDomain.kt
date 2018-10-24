package com.omvp.domain

import android.net.Uri

import org.threeten.bp.LocalDateTime

data class SampleDomain(
        var id: String,
        var title: String,
        var link: Uri,
        var pubdate: LocalDateTime,
        var imageResId: Int
) {
    constructor() : this(
            "",
            "",
            Uri.parse(""),
            LocalDateTime.now(),
            0
    )
}
