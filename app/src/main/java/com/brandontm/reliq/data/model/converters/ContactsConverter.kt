package com.brandontm.reliq.data.model.converters

import androidx.room.TypeConverter
import com.brandontm.reliq.data.model.entities.Contact
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ContactsConverter {

    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, Contact::class.java)
    val adapter = moshi.adapter<List<Contact>>(type)

    @TypeConverter
    fun jsonToContacts(value: String): List<Contact>? {
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun contactsToJson(value: List<Contact>): String {
        return adapter.toJson(value)
    }
}
