package com.devkazonovic.projects.thenews.domain

import com.devkazonovic.projects.thenews.common.extensions.toResult
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
    private val localDataSource: LocalDataSource
) {

    fun getStories(languageZoneId: String): Single<Resource<List<Story>>> {
        return remoteDataSource.getTopStories(languageZoneId).toResult()
    }

    fun getTopicStories(topicId: String, languageZoneId: String): Single<Resource<List<Story>>> {
        return remoteDataSource.getTopicStories(topicId, languageZoneId).toResult()
    }

    fun searchByKeyword(keyword: String, languageZoneId: String): Single<Resource<List<Story>>> {
        return remoteDataSource.searchByKeyword(keyword, languageZoneId).toResult()
    }

    fun saveStoryToReadLater(story: Story): Completable {
        return localDataSource.saveStoryToReadLater(story)
    }

    fun deleteStoryToReadLater(story: Story): Completable {
        return localDataSource.deleteStoryToReadLater(story)
    }

    fun getReadLaterStories(): Flowable<Resource<List<Story>>> {
        return localDataSource.getStoriesReadLater()
            .toResult()
    }
}