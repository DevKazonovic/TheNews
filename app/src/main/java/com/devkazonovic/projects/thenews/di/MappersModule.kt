package com.devkazonovic.projects.thenews.di

import com.devkazonovic.projects.thenews.domain.mapper.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    abstract fun bindMappers(mappers: MapperFactory): Mappers

    @Binds
    abstract fun bindPjoMappers(pojoMappers: PojoMappers): IPojoMappers

    @Binds
    abstract fun bindEntityMappers(entityMappers: EntityMappers): IEntityMappers

    @Binds
    abstract fun domainModelMappers(domainModelMappers: DomainModelMappers): IDomainModelMappers

}