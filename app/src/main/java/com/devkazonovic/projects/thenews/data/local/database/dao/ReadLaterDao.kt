package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.room.*
import com.devkazonovic.projects.thenews.data.local.database.entity.T_SavedStory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
abstract class ReadLaterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg savedStory: T_SavedStory): Completable

    @Delete
    abstract fun delete(vararg savedStory: T_SavedStory): Completable

    @Query("SELECT * From readlater")
    abstract fun findAll(): Flowable<List<T_SavedStory>>

}