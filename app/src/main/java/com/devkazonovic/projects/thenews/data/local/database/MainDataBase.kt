package com.devkazonovic.projects.thenews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devkazonovic.projects.thenews.data.local.database.dao.ReadLaterDao
import com.devkazonovic.projects.thenews.data.local.database.dao.StoriesDao
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity

@Database(
    entities = [
        StoryEntity::class,
        SavedStoryEntity::class,
    ], version = 12, exportSchema = false
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun storiesDao(): StoriesDao
    abstract fun readLaterDao(): ReadLaterDao

}