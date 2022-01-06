package com.devkazonovic.projects.thenews.di

import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.common.util.RxSchedulersFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RxSchedulersModule {

    @Singleton
    @Binds
    abstract fun provideSchedulers(rxSchedulers: RxSchedulersFactory): RxSchedulers
}