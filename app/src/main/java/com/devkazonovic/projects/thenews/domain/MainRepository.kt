package com.devkazonovic.projects.thenews.domain

import com.devkazonovic.projects.thenews.common.extensions.toResult
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.LocalDataSource
import com.devkazonovic.projects.thenews.data.RemoteDataSource
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.files.LanguageZoneList
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val languageZoneList: LanguageZoneList,
    private val rxSchedulers: RxSchedulers,
    mappers: Mappers,
) {

    //Todo : Write Tests

    private val domainModelMappers = mappers.domainModelMappers()
    private val entityMappers = mappers.entityMappers()
    private val pojoMappers = mappers.pojoMappers()


    fun getTopStories(
        languageZoneId: String = getCurrentLanguageZone(),
        reload: Boolean = false,
        cleanCache: Boolean = false
    ): Single<Resource<List<Story>>> {
        //Todo : Remove duplicated call to localDataSource.getLocalTopStories()
        return localDataSource.getLocalTopStories()
            .flatMap { cachedStories ->
                if (cleanCache) deleteAllLocalTopStories()
                    .blockingAwait()
                if (cachedStories.isEmpty() || reload) {
                    remoteDataSource.getRemoteTopStories(languageZoneId).flatMap { remoteStories ->
                        saveTopStories(remoteStories).blockingAwait()
                        localDataSource.getLocalTopStories()
                    }
                } else Single.just(cachedStories)
            }
            .map { items ->
                items.map { item ->
                    entityMappers.storyEntityMapper().toDomainModel(item)
                }
            }
            .toResult()

    }

    fun getTopicStories(
        languageZoneId: String = getCurrentLanguageZone(),
        topicId: String,
        reload: Boolean = false,
        cleanCache: Boolean = false
    ): Single<Resource<List<Story>>> {
        //Todo : Remove duplicated call to localDataSource.getLocalTopicStories(topicId)
        return localDataSource.getLocalTopicStories(topicId)
            .flatMap { localTopicStories ->
                if (cleanCache) deleteLocalTopicStoriesByTopicId(topicId).blockingAwait()
                if (localTopicStories.isEmpty() || reload) {
                    remoteDataSource.getRemoteTopicStories(languageZoneId, topicId)
                        .flatMap { remoteTopicStories ->
                            saveTopicStories(remoteTopicStories, topicId).blockingAwait()
                            localDataSource.getLocalTopicStories(topicId)
                        }
                } else Single.just(localTopicStories)
            }.map { items ->
                items.map { item ->
                    entityMappers.topicStoryMapper().toDomainModel(item)
                }
            }.toResult()
    }

    fun getSavedStories(): Flowable<Resource<List<Story>>> {
        return localDataSource.getLocalSavedStories()
            .map { items ->
                items.map { item ->
                    entityMappers.savedStoryMapper().toDomainModel(item)
                }
            }
            .toResult()
    }

    fun getSupportedLanguagesZones(): Single<List<LanguageZone>> {
        return Single.just(languageZoneList.getList())
    }

    fun searchByKeyword(
        keyword: String,
        languageZoneId: String = getCurrentLanguageZone()
    ): Single<Resource<List<Story>>> {
        return remoteDataSource.searchByKeyword(ceid = languageZoneId, keyword = keyword)
            .map { items ->
                items.map { item ->
                    pojoMappers.topStoryPojoMapper().toDomainModel(item)
                }
            }.toResult()
    }

    fun saveStoryToReadLater(story: Story): Completable {
        return localDataSource.saveStoryToSavedStories(
            domainModelMappers.savedStoryModelMapper().toEntity(story)
        )
    }

    fun deleteStoryToReadLater(story: Story): Completable {
        return localDataSource.deleteStoryFromSavedStories(
            domainModelMappers.savedStoryModelMapper().toEntity(story)
        )
    }

    fun deleteAllLocalTopicStories(): Completable {
        return Completable.fromAction {
            localDataSource.deleteAllLocalTopicStories()
        }
    }

    fun deleteAllLocalTopStories(): Completable {
        return Completable.fromAction {
            localDataSource.deleteAllLocalTopStories()
        }
    }

    fun deleteLocalTopicStoriesByTopicId(topicId: String): Completable {
        return Completable.fromAction {
            localDataSource.deleteLocalTopicStoriesByTopicId(topicId)
        }
    }

    fun isStorySavedToReadLater(url: String): Maybe<SavedStoryEntity> {
        return localDataSource.isStorySaved(url)
    }

    fun getLanguageZoneObservable(): Observable<String> {
        return localDataSource.getLanguageZoneObservable()
    }

    fun getCurrentLanguageZone(): String {
        return localDataSource.getLanguageZone()
    }

    fun saveLanguageZone(languageZoneId: String): Boolean {
        return localDataSource.saveLanguageZone(languageZoneId)
    }

    fun saveTopStories(stories: List<Item>): Completable {
        return localDataSource.saveTopStories(
            stories.map { pojoMappers.topStoryPojoMapper().toEntity(it) }
        )
    }

    fun saveTopicStories(stories: List<Item>, topicId: String): Completable {
        return localDataSource.saveTopicStories(
            stories.map { pojoMappers.topicStoryPojoMapper().toEntity(it).copy(topicId = topicId) }
        )
    }

}