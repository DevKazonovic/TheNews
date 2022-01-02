package com.devkazonovic.projects.thenews.domain.mapper

import javax.inject.Inject

interface Mappers {
    fun pojoMappers(): PojoMappers
    fun entityMappers(): EntityMappers
    fun domainModelMappers(): DomainModelMappers
}

class MapperFactory @Inject constructor(
    private val pojoMappers: PojoMappers,
    private val entityMappers: EntityMappers,
    private val domainModelMappers: DomainModelMappers
) : Mappers {

    override fun pojoMappers(): PojoMappers = pojoMappers
    override fun entityMappers(): EntityMappers = entityMappers
    override fun domainModelMappers(): DomainModelMappers = domainModelMappers

}