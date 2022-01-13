package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.mainDataBase
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.core.Completable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import timber.log.Timber


@RunWith(AndroidJUnit4::class)
class SavedStoriesDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: MainDataBase
    private lateinit var savedStoriesDao: SavedStoriesDao

    @Test
    fun test_whenInsertingItemsWithDifferentUrl_thenComplete() {
        savedStoriesDao.insert(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenInsertingItemsWithSameUrl_thenInsertOnlyOne_and_Complete() {

        savedStoriesDao.insert(story1, story1)
            .test()
            .assertComplete()

        savedStoriesDao.findAll()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(1)
                    containsExactly(story1)
                }
                true
            }
    }

    @Test
    fun test_whenDeletingItems_thenComplete() {
        savedStoriesDao.insert(story1, story2)
            .blockingAwait()
        savedStoriesDao.delete(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenDeletingAllItems_thenComplete() {
        savedStoriesDao.insert(story1, story2)
            .blockingAwait()

        Completable.fromAction { savedStoriesDao.deleteAll() }
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenQueryingAllTable_thenReturnAllItems_and_Complete() {
        savedStoriesDao.insert(story1, story2)
            .blockingAwait()
        savedStoriesDao.findAll()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                    containsExactly(story1, story2)
                }
                true
            }
    }

    @Test
    fun test_whenQueryingByUrl_thenReturnItemsWithSameUrl_and_Complete() {

        savedStoriesDao.insert(story1, story2)
            .blockingAwait()

        savedStoriesDao.findByUrl(story1.url)
            .test()
            .assertValue { savedEntity ->
                assertThat(savedEntity).isEqualTo(story1)
                true
            }.assertComplete()
    }


    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        try {
            database = mainDataBase(getApplicationContext())
        } catch (e: Exception) {
            Timber.i(e.message!!)
        }

        savedStoriesDao = database.savedStoriesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        private val source1 = SourceEntity("source1", "Source1", "source_url1")
        private val source2 = SourceEntity("source2", "Source2", "source_url2")

        private val story1 =
            SavedStoryEntity("url1", "title1", sourceEntity = source1)
        private val story2 =
            SavedStoryEntity("url2", "title2", sourceEntity = source2)
    }
}