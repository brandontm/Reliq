package com.brandontm.reliq.data.model.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Contact constructor(
    val id: String,
    var name: String = "",
    var score: Int = 0)
