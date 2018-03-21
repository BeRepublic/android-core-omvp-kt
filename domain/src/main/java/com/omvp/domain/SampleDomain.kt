package com.omvp.domain

import android.net.Uri

import org.parceler.Parcel
import org.threeten.bp.LocalDateTime

import lombok.Data

data class SampleDomain(var id: Long, var title: String, var link: Uri, var pubdate: LocalDateTime)
