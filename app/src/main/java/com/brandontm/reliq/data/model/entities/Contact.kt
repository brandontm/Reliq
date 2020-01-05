package com.brandontm.reliq.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "contacts")
data class Contact constructor(
    @PrimaryKey
    val id: String,
    var name: String = "",
    var age: Int = 0,
    var score: Int = 0)
