package com.devkazonovic.projects.thenews.common.extensions

import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.domain.model.Resource
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

fun <T : Any> Single<T>.toResult(): Single<Resource<T>> = this
    .map<Resource<T>> { Resource.Success(it) }
    .onErrorReturn { exception ->
        Timber.e("Error[RxJava] : $exception")
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

fun <T : Any> Flowable<T>.toResult(): Flowable<Resource<T>> = this
    .map<Resource<T>> { Resource.Success(it) }
    .onErrorReturn { exception ->
        Timber.e("Error[RxJava] : $exception")
        when (exception) {
            is SocketException -> {
                Resource.Error(R.string.error_unknown)
            }
            is IOException -> {
                Resource.Error(R.string.error_unknown)
            }
            else -> {
                throw RuntimeException(exception)
            }
        }
    }
