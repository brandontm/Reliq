package com.brandontm.reliq.di.viewModel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModuleFactory(viewModelFactory: ViewModelProviderFactory)
            : ViewModelProvider.Factory

}
