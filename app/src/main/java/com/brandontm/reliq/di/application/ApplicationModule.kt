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
