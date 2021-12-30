package com.devkazonovic.projects.thenews.data

import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    mainDataBase: MainDataBase,
    private val mappers: Mappers
) {

    //Todo: Add CRUD Operations & Test it and then test Repository

    private val readLaterDao = mainDataBase.readLaterDao()

    fun saveStoryToReadLater(story: Story): Completable {
        return readLaterDao.insert(mappers.savedStoryDataModelEntity().map(story))
    }

    fun deleteStoryToReadLater(story: Story): Completable {
        return readLaterDao.delete(mappers.savedStoryDataModelEntity().map(story))
    }

    fun getStoriesReadLater(): Flowable<List<Story>> {
        return readLaterDao.findAll()
            .map { stories ->
                stories.map { mappers.savedStoryEntityDataModel().map(it) }
            }
    }

}