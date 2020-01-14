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

package com.brandontm.reliq.ui.contacts.detail

import androidx.lifecycle.MutableLiveData
import com.brandontm.reliq.base.BaseViewModel
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val userRepository: UserRepository): BaseViewModel() {
    val updateContactStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun updateContact(contact: Contact) {
        userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { user ->
                    val contactToUpdate: Contact = user.contacts.first { it.id == contact.id }
                    if(contactToUpdate == contact) {
                        return@subscribeBy
                    }

                    val contactsEditable = user.contacts as MutableList<Contact>

                    contactsEditable[user.contacts.indexOf(contactToUpdate)] = contact
                    user.contacts = contactsEditable

                    userRepository.saveUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = {
                                updateContactStatus.value = true
                            },
                            onError = {
                                Timber.e(it, "Error updating contact \"$contact\" from user \"$user\"")
                                updateContactStatus.value = false
                            }
                        )
                        .addTo(disposables)
                },
                onError = {
                    Timber.e(it, "Error getting user while updating contact \"$contact\"")
                    updateContactStatus.value = false
                }
            )
            .addTo(disposables)
    }


}
