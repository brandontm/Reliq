package com.brandontm.reliq.data.db

import android.content.Context
import androidx.room.Room
import com.brandontm.reliq.data.db.dao.UserDao
import com.brandontm.reliq.di.qualifiers.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ForApplication context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "reliqdb").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
