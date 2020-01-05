package com.brandontm.reliq.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.brandontm.reliq.data.model.entities.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getContacts(): List<Contact>
}
