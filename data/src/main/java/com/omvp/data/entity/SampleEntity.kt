package com.omvp.data.entity

import com.google.gson.annotations.Expose

data class SampleEntity(@Expose private val id: String,
                        @Expose private val title: String,
                        @Expose private val link: String,
                        @Expose private val pubdate: Long)
