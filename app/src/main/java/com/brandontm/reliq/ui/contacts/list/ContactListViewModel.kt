package com.brandontm.reliq.ui.contacts.list

import androidx.lifecycle.MutableLiveData
import com.brandontm.reliq.base.BaseViewModel
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactListViewModel @Inject constructor(val userRepository: UserRepository) : BaseViewModel() {
    val contacts: MutableLiveData<List<Contact>> = MutableLiveData()

    fun retrieveContacts() {
        userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    contacts.value = it.contacts!!
                }
            )
            .addTo(disposables)
    }
}
