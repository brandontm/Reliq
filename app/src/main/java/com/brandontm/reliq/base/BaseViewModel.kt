package com.brandontm.reliq.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    @CallSuper
    fun onDestroy() {
        clearSubscriptions()
    }

    private fun clearSubscriptions() {
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }


    @CallSuper
    open fun cancel(){
        clearSubscriptions()
    }
}