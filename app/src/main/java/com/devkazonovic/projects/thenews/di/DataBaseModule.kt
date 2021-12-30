package com.devkazonovic.projects.thenews.di

import android.content.Context
import androidx.room.Room
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Volatile
    private var INSTANCE: MainDataBase? = null

    @Provides
    fun provideMainDataBase(@ApplicationContext context: Context): MainDataBase {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "main_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}