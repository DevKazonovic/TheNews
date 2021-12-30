package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.T_SavedStory
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class StoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg story: T_Story): Completable

    @Delete
    abstract fun delete(vararg story: T_Story): Completable

    @Transaction
    @Query("SELECT * FROM story")
    abstract fun findAll(): Single<List<T_Story>>

    @Transaction
    @Query("SELECT * FROM story WHERE topicId=:topicId")
    abstract fun findTopicStories(topicId: String): Single<List<T_Story>>

    @Query("SELECT * FROM readlater")
    abstract fun findReadLaterStories(): Flowable<T_SavedStory>

}