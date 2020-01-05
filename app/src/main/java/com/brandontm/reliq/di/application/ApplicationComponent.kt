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
