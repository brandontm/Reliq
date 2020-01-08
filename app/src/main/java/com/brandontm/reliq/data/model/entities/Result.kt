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
