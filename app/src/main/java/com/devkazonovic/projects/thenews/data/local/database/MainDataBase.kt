package com.devkazonovic.projects.thenews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devkazonovic.projects.thenews.data.local.database.dao.SavedStoriesDao
import com.devkazonovic.projects.thenews.data.local.database.dao.TopStoriesDao
import com.devkazonovic.projects.thenews.data.local.database.dao.TopicStoriesDao
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity

@Database(
    entities = [
        TopStoryEntity::class,
        SavedStoryEntity::class,
        TopicStoryEntity::class
    ], version = 14, exportSchema = false
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun topicStoriesDao(): TopicStoriesDao
    abstract fun topStoriesDao(): TopStoriesDao
    abstract fun savedStoriesDao(): SavedStoriesDao

}