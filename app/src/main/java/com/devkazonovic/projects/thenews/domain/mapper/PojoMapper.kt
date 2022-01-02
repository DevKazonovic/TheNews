package com.devkazonovic.projects.thenews.domain.mapper

interface PojoMapper<P, E, D> {
    fun toEntity(input: P?): E
    fun toDomainModel(input: P?): D

}