package com.brandontm.reliq.data.repository

import com.brandontm.reliq.data.db.dao.UserDao
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.Result
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override fun getUser(): Maybe<User> {
        return userDao.getUser()
    }

    override fun getContacts(): Observable<Result<List<Contact>>> {
        return userDao.getUser().toObservable().compose { item ->
            item.map { Result.success(it.contacts) }
                .onErrorReturn { e -> Result.failure(e) }
                .startWith(Result.loading())
        }
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
