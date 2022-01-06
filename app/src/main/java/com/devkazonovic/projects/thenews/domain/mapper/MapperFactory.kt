package com.devkazonovic.projects.thenews.domain.mapper

import javax.inject.Inject

interface Mappers {
    fun pojoMappers(): IPojoMappers
    fun entityMappers(): IEntityMappers
    fun domainModelMappers(): IDomainModelMappers
}

class MapperFactory @Inject constructor(
    private val pojoMappers: IPojoMappers,
    private val entityMappers: IEntityMappers,
    private val domainModelMappers: IDomainModelMappers
) : Mappers {
    override fun pojoMappers(): IPojoMappers = pojoMappers
    override fun entityMappers(): IEntityMappers = entityMappers
    override fun domainModelMappers(): IDomainModelMappers = domainModelMappers
}