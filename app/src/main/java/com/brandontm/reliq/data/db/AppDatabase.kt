package com.brandontm.reliq.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.db.dao.ContactDao
import com.brandontm.reliq.data.model.entities.User
import com.brandontm.reliq.data.db.dao.UserDao
import com.brandontm.reliq.data.model.converters.ContactsConverter

@Database(
    entities = [User::class, Contact::class],
    version = 1
)
@TypeConverters(ContactsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun userDao(): UserDao
}
