package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class TopStoriesDao {

    companion object {
        private const val TABLE_TOP_STORIES = "topstories"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg topStoryEntity: TopStoryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(topStoryEntity: List<TopStoryEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAndReturn(topStoryEntity: TopStoryEntity): Single<Long>

    @Delete
    abstract fun delete(vararg topStoryEntity: TopStoryEntity): Completable

    @Transaction
    @Query("SELECT * FROM $TABLE_TOP_STORIES")
    abstract fun findAll(): Single<List<TopStoryEntity>>

    @Query("DELETE FROM $TABLE_TOP_STORIES")
    abstract fun deleteAll()

}