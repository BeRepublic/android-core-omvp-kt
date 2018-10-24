package com.omvp.data.entity

import com.google.gson.annotations.Expose

data class SampleEntity(
        @Expose val id: String,
        @Expose val title: String,
        @Expose val link: String,
        @Expose val pubdate: Long
)
