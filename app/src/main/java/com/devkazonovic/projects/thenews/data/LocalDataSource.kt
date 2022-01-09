package com.devkazonovic.projects.thenews.data

import com.devkazonovic.projects.thenews.common.util.ConvertersUtil
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    mainDataBase: MainDataBase,
    private val mappers: Mappers
) {

    private val readLaterDao = mainDataBase.readLaterDao()
    private val storiesDao = mainDataBase.storiesDao()

    fun getCachedStories(): Single<List<Story>> {
        return storiesDao.findTopStories().map { stories ->
            stories.map { mappers.entityMappers().storyEntityMapper().toDomainModel(it) }
        }
    }

    fun getStoriesReadLater(): Flowable<List<Story>> {
        return readLaterDao.findAll()
            .map { stories ->
                stories
                    .reversed()
                    .map { mappers.entityMappers().savedStoryMapper().toDomainModel(it) }
            }
    }

    fun saveStoryToReadLater(story: Story): Completable {
        return readLaterDao.insert(
            mappers.domainModelMappers().savedStoryModelMapper().toEntity(story)
        )
    }

    fun deleteStoryToReadLater(story: Story): Completable {
        return readLaterDao.delete(
            mappers.domainModelMappers().savedStoryModelMapper().toEntity(story)
        )
    }

    fun saveStory(story: Story): Single<Long> {
        return storiesDao.insertAndReturn(
            mappers.domainModelMappers().storyModelMapper().toEntity(story)
        )
    }

    fun saveStoriesToCache(stories: List<Story>): Completable {
        return Observable.fromIterable(stories)
            .map { item -> mappers.domainModelMappers().storyModelMapper().toEntity(item) }
            .flatMapCompletable {
                storiesDao.insert(it)
            }
    }

    fun updateStorySaveState(story: Story, save: Boolean): Completable {
        return storiesDao.updateStorySaveState(story.url, ConvertersUtil.fromBooleanToInt(save))
    }

    fun deleteAllCachedStories() {
        storiesDao.deleteTopStories()
    }

    fun isStorySaved(url: String): Maybe<SavedStoryEntity> {
        return readLaterDao.isStorySaved(url)
    }

}