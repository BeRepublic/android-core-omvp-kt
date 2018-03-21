package com.omvp.domain

import android.net.Uri

import org.threeten.bp.LocalDateTime

data class SampleDomain(var id: Long, var title: String, var link: Uri, var pubdate: LocalDateTime)
