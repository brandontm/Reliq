package com.brandontm.reliq.di.main

import androidx.lifecycle.ViewModel
import com.brandontm.reliq.di.viewModel.ViewModelKey
import com.brandontm.reliq.ui.contacts.list.ContactListFragment
import com.brandontm.reliq.ui.contacts.list.ContactListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun contributesFriendsListFragment(): ContactListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    internal abstract fun bindFriendsListViewModel(contactListViewModel: ContactListViewModel): ViewModel
}
