package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
abstract class SavedStoriesDao {
    companion object {
        private const val TABLE_SAVED_STORIES = "savedstories"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg savedStoryEntity: SavedStoryEntity): Completable

    @Delete
    abstract fun delete(vararg savedStoryEntity: SavedStoryEntity): Completable

    @Query("SELECT * From $TABLE_SAVED_STORIES")
    abstract fun findAll(): Flowable<List<SavedStoryEntity>>

    @Query("SELECT * FROM $TABLE_SAVED_STORIES WHERE url=:url")
    abstract fun findByUrl(url: String): Maybe<SavedStoryEntity>

    @Query("DELETE FROM $TABLE_SAVED_STORIES")
    abstract fun deleteAll()

}