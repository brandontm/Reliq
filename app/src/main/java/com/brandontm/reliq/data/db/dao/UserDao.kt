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
