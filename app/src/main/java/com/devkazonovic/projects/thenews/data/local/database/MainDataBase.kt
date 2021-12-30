package com.devkazonovic.projects.thenews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devkazonovic.projects.thenews.data.local.database.dao.ReadLaterDao
import com.devkazonovic.projects.thenews.data.local.database.dao.StoriesDao
import com.devkazonovic.projects.thenews.data.local.database.entity.T_SavedStory
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story

@Database(
    entities = [
        T_Story::class,
        T_SavedStory::class,
    ], version = 5, exportSchema = false
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun storiesDao(): StoriesDao
    abstract fun readLaterDao(): ReadLaterDao

}