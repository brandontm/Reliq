package com.brandontm.reliq.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Maybe<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User): Completable
}
