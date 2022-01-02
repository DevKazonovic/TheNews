package com.devkazonovic.projects.thenews.domain.mapper

interface EntityMapper<E, D, P> {
    fun toPojo(input: E?): P
    fun toDomainModel(input: E?): D
}