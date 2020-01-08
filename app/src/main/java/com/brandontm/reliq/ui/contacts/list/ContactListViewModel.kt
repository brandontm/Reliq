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

package com.brandontm.reliq.ui.contacts.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.brandontm.reliq.base.BaseViewModel
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.Result
import com.brandontm.reliq.data.model.entities.User
import com.brandontm.reliq.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val userRepository: UserRepository)
    : BaseViewModel() {

    private val TAG: String = ContactListViewModel::class.java.simpleName

    val user: MutableLiveData<User> = MutableLiveData()
    val contacts: MutableLiveData<Result<List<Contact>>> = MutableLiveData()
    val saveContactsStatus: MutableLiveData<Boolean> = MutableLiveData()

    init {
        initSession()
    }

    fun retrieveContacts() {
        userRepository.getContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    contacts.value = it
                },
                onError = {
                    Log.e(TAG, "Error retrieving contacts", it)
                }
            )
            .addTo(disposables)
    }

    fun saveUser(user: User) {
        userRepository.saveUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveContactsStatus.value = true
                },
                onError = {
                    Log.e(TAG, "Error saving user $user", it)
                }
            )
            .addTo(disposables)
    }

    fun deleteUser(user: User) {
        userRepository.deleteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveContactsStatus.value = true
                },
                onError = {
                    Log.e(TAG, "Error deleting user $user", it)
                }
            )
            .addTo(disposables)
    }

    private fun initSession() {
        userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    user.value = it
                },
                onComplete = {

                    createSession()

                },
                onError = {
                    Log.e(TAG, "Error retrieving user", it)
                }
            )
            .addTo(disposables)
    }

    private fun createSession() {
        val insertUser = User("user", "User", listOf())
        userRepository.saveUser(insertUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    user.value = insertUser
                },
                onError = {
                    Log.e(TAG, "Error saving user $insertUser", it)
                }
            )
            .addTo(disposables)
    }
}
