package com.brandontm.reliq.di.activity

import com.brandontm.reliq.di.main.MainModule
import com.brandontm.reliq.di.scopes.PerActivity
import com.brandontm.reliq.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
