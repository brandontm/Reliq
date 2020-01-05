package com.brandontm.reliq.data.repository

import com.brandontm.reliq.data.db.dao.UserDao
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Completable
import io.reactivex.Maybe

class UserRepositoryImpl(val userDao: UserDao) : UserRepository {
    override fun getUser(): Maybe<User> {
        return userDao.getUser()
    }

    override fun saveUser(user: User): Completable {
        return userDao.saveUser(user)
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