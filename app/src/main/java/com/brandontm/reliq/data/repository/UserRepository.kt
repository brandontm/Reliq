package com.brandontm.reliq.data.repository

import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.Result
import com.brandontm.reliq.data.model.entities.User
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Maybe<User>
    fun getContacts(): Observable<Result<List<Contact>>>
    fun saveUser(user: User): Single<Long>
    fun deleteUser(user: User): Single<Int>
}
