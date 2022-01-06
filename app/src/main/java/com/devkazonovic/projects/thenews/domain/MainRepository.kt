package com.devkazonovic.projects.thenews.domain

import com.devkazonovic.projects.thenews.common.extensions.toResult
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.LocalDataSource
import com.devkazonovic.projects.thenews.data.RemoteDataSource
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val rxSchedulers: RxSchedulers
) {

    fun getStories(languageZoneId: String, reload: Boolean = false): Single<Resource<List<Story>>> {
        return localDataSource.getCachedStories().flatMap { cachedStories ->
            if (cachedStories.isEmpty() || reload) {
                if (cachedStories.isNotEmpty())
                    Completable.fromCallable {
                        localDataSource.deleteAllCachedStories()
                    }.subscribeOn(rxSchedulers.ioScheduler()).subscribe()

                remoteDataSource.getTopStories(languageZoneId).flatMap { remoteStories ->
                    localDataSource.saveStoriesToCache(remoteStories)
                        .blockingAwait()
                    localDataSource.getCachedStories()
                }
            } else Single.just(cachedStories)
        }.toResult()

    }

    fun getTopicStories(topicId: String, languageZoneId: String): Single<Resource<List<Story>>> {
        return remoteDataSource.getTopicStories(ceid = languageZoneId, topicId = topicId).toResult()
    }

    fun getReadLaterStories(): Flowable<Resource<List<Story>>> {
        return localDataSource.getStoriesReadLater()
            .toResult()
    }

    fun searchByKeyword(keyword: String, languageZoneId: String): Single<Resource<List<Story>>> {
        return remoteDataSource.searchByKeyword(ceid = languageZoneId, keyword = keyword).toResult()
    }

    fun saveStoryToReadLater(story: Story): Completable {
        return localDataSource.saveStoryToReadLater(story)
    }

    fun deleteStoryToReadLater(story: Story): Completable {
        return localDataSource.deleteStoryToReadLater(story)
    }

}