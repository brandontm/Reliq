/*
 * Copyright (C) 2019  Brandon Tirado
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
