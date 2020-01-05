package com.brandontm.reliq.ui.main.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brandontm.reliq.base.BaseViewModel
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.User
import com.brandontm.reliq.data.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FriendsListViewModel @Inject constructor(val userRepository: UserRepository) : BaseViewModel() {
    fun saveUser() {
        val user = User("asdad", "John", 30,
            listOf(
                Contact("11", "Joshua", 13, 100)
            )
        )

        userRepository.saveUser(user)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = { throw it },
                onComplete = { Log.d("TEST", "COMPLETE") }
            ).addTo(disposables)
    }

    fun getUser() {
        userRepository.getUser()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    val user = it
                    Log.d("ASD", user.name)
                },
                onComplete = { Log.d("TEST", "COMPLETE") },
                onError = { Log.d("TEST", "ERROR") }
            ).addTo(CompositeDisposable())
    }
}
