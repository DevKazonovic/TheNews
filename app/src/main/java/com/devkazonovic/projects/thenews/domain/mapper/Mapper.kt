package com.devkazonovic.projects.thenews.domain.mapper

/**Mappers (DTOs <=> Data Models) need to implement this interface*/
interface Mapper<I, O> {
    fun map(input: I? = null): O
}