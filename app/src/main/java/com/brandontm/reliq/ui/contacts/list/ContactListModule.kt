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

import androidx.lifecycle.ViewModel
import com.brandontm.reliq.di.scopes.PerFragment
import com.brandontm.reliq.di.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ContactListModule {
    @ContributesAndroidInjector
    @PerFragment
    internal abstract fun contributesContactListFragment(): ContactListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    @PerFragment
    internal abstract fun bindContactListViewModel(contactListViewModel: ContactListViewModel): ViewModel
}