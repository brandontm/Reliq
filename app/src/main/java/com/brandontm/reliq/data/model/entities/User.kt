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
    var contacts: List<Contact>) {

    fun addContact(contact: Contact) {
        contacts = contacts.toMutableList().apply { this.add(contact) }.toList()
    }

    fun removeContact(contact: Contact) {
        contacts = contacts.toMutableList().apply { this.remove(contact) }.toList()
    }
}
