package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class TopicStoriesDao {

    companion object {
        private const val TABLE_TOPIC_STORIES = "topicstories"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg story: TopicStoryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(topStoryEntity: List<TopicStoryEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAndReturn(story: TopicStoryEntity): Single<Long>

    @Delete
    abstract fun delete(vararg stories: TopicStoryEntity): Completable

    @Query("SELECT * FROM $TABLE_TOPIC_STORIES")
    abstract fun findAll(): Single<List<TopicStoryEntity>>

    @Query("SELECT * FROM $TABLE_TOPIC_STORIES WHERE topic_id=:topicId")
    abstract fun findByTopic(topicId: String): Single<List<TopicStoryEntity>>

    @Query("DELETE FROM $TABLE_TOPIC_STORIES WHERE topic_id=:topicId")
    abstract fun deleteByTopic(topicId: String)

    @Query("DELETE FROM $TABLE_TOPIC_STORIES")
    abstract fun deleteAll()
}