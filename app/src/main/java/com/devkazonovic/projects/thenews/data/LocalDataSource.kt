package com.devkazonovic.projects.thenews.data

import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    mainDataBase: MainDataBase,
    private val localKeyValue: LocalKeyValue,
) {
    //Todo : Write Tests

    private val topStoriesDao = mainDataBase.topStoriesDao()
    private val topicStoriesDao = mainDataBase.topicStoriesDao()
    private val savedStoriesDao = mainDataBase.savedStoriesDao()

    fun getLocalSavedStories(): Flowable<List<SavedStoryEntity>> {
        return savedStoriesDao.findAll()
    }

    fun getLocalTopStories(): Single<List<TopStoryEntity>> {
        return topStoriesDao.findAll()
    }

    fun getLocalTopicStories(topicId: String): Single<List<TopicStoryEntity>> {
        return topicStoriesDao.findByTopic(topicId)
    }

    fun getLanguageZoneObservable(): Observable<String> {
        return localKeyValue.languageZoneObservable
    }

    fun getLanguageZone(): String {
        return localKeyValue.getLanguageZone()
    }

    fun saveLanguageZone(languageZoneId: String): Boolean {
        return localKeyValue.saveLanguageZone(languageZoneId)
    }

    fun saveStoryToSavedStories(story: SavedStoryEntity): Completable {
        return savedStoriesDao.insert(story)
    }

    fun saveTopStories(stories: List<TopStoryEntity>): Completable {
        return topStoriesDao.insert(stories)
    }

    fun saveTopicStories(stories: List<TopicStoryEntity>): Completable {
        return topicStoriesDao.insert(stories)
    }

    fun deleteStoryFromSavedStories(story: SavedStoryEntity): Completable {
        return savedStoriesDao.delete(story)
    }

    fun deleteAllLocalTopStories() {
        topStoriesDao.deleteAll()
    }

    fun deleteLocalTopicStoriesByTopicId(topicId: String) {
        topicStoriesDao.deleteByTopic(topicId)
    }

    fun deleteAllLocalTopicStories() {
        topicStoriesDao.deleteAll()
    }

    fun isStorySaved(url: String): Maybe<SavedStoryEntity> {
        return savedStoriesDao.findByUrl(url)
    }

}