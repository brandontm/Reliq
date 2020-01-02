package com.brandontm.reliq.di.main

import androidx.lifecycle.ViewModel
import com.brandontm.reliq.di.scopes.PerActivity
import com.brandontm.reliq.di.viewModel.ViewModelKey
import com.brandontm.reliq.ui.main.list.FriendsListFragment
import com.brandontm.reliq.ui.main.list.FriendsListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun contributesFriendsListFragment(): FriendsListFragment

    @Binds
    @IntoMap
    @ViewModelKey(FriendsListViewModel::class)
    internal abstract fun bindFriendsListViewModel(friendsListViewModel: FriendsListViewModel): ViewModel
}
