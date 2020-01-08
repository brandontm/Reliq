package com.brandontm.reliq.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.brandontm.reliq.data.model.converters.ContactsConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user")
data class User constructor(
    @PrimaryKey
    val _id: String,
    var name: String = "",
    @TypeConverters(ContactsConverter::class)
    var contacts: List<Contact>)
