package com.devkazonovic.projects.thenews.domain.mapper

interface DomainModelMapper<D, E, P> {
    fun toPojo(input: D?): P
    fun toEntity(input: D?): E
}