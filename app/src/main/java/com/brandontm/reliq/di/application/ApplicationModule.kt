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

package com.brandontm.reliq.di.application

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.brandontm.reliq.di.qualifiers.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Provides
    @ForApplication
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideLayoutInflater(@ForApplication context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }
}
