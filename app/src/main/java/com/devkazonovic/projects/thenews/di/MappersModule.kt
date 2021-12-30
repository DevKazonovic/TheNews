package com.devkazonovic.projects.thenews.di

import com.devkazonovic.projects.thenews.domain.mapper.MapperFactory
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    abstract fun bindMappers(mappers: MapperFactory): Mappers

}