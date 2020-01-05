package com.brandontm.reliq.data.repository

import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Completable
import io.reactivex.Maybe

interface UserRepository {
    fun getUser(): Maybe<User>
    fun saveUser(user: User): Completable
}
