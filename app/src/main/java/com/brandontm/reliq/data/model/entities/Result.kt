package com.brandontm.reliq.data.model.entities

sealed class Result<out T> {
    class Loading: Result<Nothing>() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    data class Success<T>(val data: T): Result<T>()
    data class Failure(val exception: Throwable, val message: String? = null): Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> failure(error: Throwable, message: String?): Result<T> = Failure(error, message)
        fun <T> failure(error: Throwable): Result<T> = Failure(error)
        fun <T> loading(): Result<T> = Loading()
    }
}
