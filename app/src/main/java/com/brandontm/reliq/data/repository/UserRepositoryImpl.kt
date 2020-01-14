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

package com.brandontm.reliq.data.repository

import com.brandontm.reliq.data.db.dao.UserDao
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Maybe
import io.reactivex.Single

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override fun getUser(): Maybe<User> {
        return userDao.getUser()
    }

    override fun saveUser(user: User): Single<Long> {
        return userDao.saveUser(user)
    }

    override fun deleteUser(user: User): Single<Int> {
        return userDao.deleteUser(user)
    }


    companion object {
        private var instance: UserRepositoryImpl? = null

        fun getInstance(userDao: UserDao): UserRepositoryImpl {
            if (instance == null) {
                instance = UserRepositoryImpl(userDao)
            }
            return instance!!
        }
    }
}
