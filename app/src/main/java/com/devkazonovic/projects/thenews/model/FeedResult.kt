package com.devkazonovic.projects.thenews.model

sealed class FeedResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : FeedResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : FeedResult<T>(data, message)

    class Loading<T> : FeedResult<T>()

}
