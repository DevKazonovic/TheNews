package com.devkazonovic.projects.thenews.common.extensions

import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.domain.model.Resource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import java.net.SocketException

fun <T> Single<T>.toResult(): Single<Resource<T>> = this
    .map<Resource<T>> { Resource.Success(it) }
    .onErrorReturn { exception ->
        when (exception) {
            is SocketException -> {
                Resource.Error(R.string.error_unknown)
            }
            is IOException -> {
                Resource.Error(R.string.error_unknown)
            }
            else -> {
                Resource.Error(R.string.error_unknown)
            }
        }
    }

fun <T> Flowable<T>.toResult(): Flowable<Resource<T>> = this
    .map<Resource<T>> { Resource.Success(it) }
    .onErrorReturn { exception ->
        when (exception) {
            is SocketException -> {
                Resource.Error(R.string.error_unknown)
            }
            is IOException -> {
                Resource.Error(R.string.error_unknown)
            }
            else -> {
                Resource.Error(R.string.error_unknown)
            }
        }
    }
