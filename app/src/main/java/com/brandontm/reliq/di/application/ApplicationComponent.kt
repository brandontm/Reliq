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
import com.brandontm.reliq.ReliqApp
import com.brandontm.reliq.data.db.DbModule
import com.brandontm.reliq.data.repository.RepositoryModule
import com.brandontm.reliq.di.activity.ActivityBuildersModule
import com.brandontm.reliq.di.main.MainModule
import com.brandontm.reliq.di.viewModel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuildersModule::class,
    DbModule::class,
    RepositoryModule::class
])
interface ApplicationComponent : AndroidInjector<ReliqApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
