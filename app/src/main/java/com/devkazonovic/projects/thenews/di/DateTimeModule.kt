package com.devkazonovic.projects.thenews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.Clock

@Module
@InstallIn(SingletonComponent::class)
object DateTimeModule {

    @Provides
    fun provideClock() : Clock{
        return Clock.systemUTC()
    }

}