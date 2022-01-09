package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class StoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg storyEntity: StoryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAndReturn(storyEntity: StoryEntity): Single<Long>

    @Query("UPDATE story SET isReadLater=:save WHERE url=:storyUrl")
    abstract fun updateStorySaveState(storyUrl: String, save: Int): Completable

    @Delete
    abstract fun delete(vararg storyEntity: StoryEntity): Completable

    @Transaction
    @Query("SELECT * FROM story")
    abstract fun findTopStories(): Single<List<StoryEntity>>

    @Transaction
    @Query("SELECT * FROM story WHERE topicId=:topicId")
    abstract fun findTopicStories(topicId: String): Single<List<StoryEntity>>

    @Query("SELECT * FROM readlater")
    abstract fun findReadLaterStories(): Flowable<SavedStoryEntity>

    @Query("DELETE FROM story")
    abstract fun deleteTopStories()

}