package com.brandontm.reliq.data.db.dao

import androidx.room.*
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Maybe<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User): Single<Long>

    @Delete
    fun deleteUser(user: User): Single<Int>
}
