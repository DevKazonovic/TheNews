package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.mainDataBase
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
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
class TopStoriesDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: MainDataBase
    private lateinit var topStoriesDao: TopStoriesDao


    @Test
    fun test_whenInsertingItemsWithDifferentUrl_thenComplete() {
        topStoriesDao.insert(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenInsertingItemWithSameUrl_thenCompleteButOnlyInsertOne() {

        topStoriesDao.insert(story1, story1)
            .test()
            .assertComplete()

        topStoriesDao.findAll()
            .test()
            .assertValue { stories ->
                assertThat(stories).hasSize(1)
                assertThat(stories).containsExactly(story1)
                true
            }.assertComplete()

    }

    @Test
    fun test_whenDeletingItems_thenComplete() {
        topStoriesDao.insert(story1, story2)
            .blockingAwait()

        topStoriesDao.delete(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenQueryingAllTable_thenReturnAllItems_and_Complete() {
        topStoriesDao.insert(story1, story2)
            .blockingAwait()
        topStoriesDao.findAll()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                    containsExactly(story1, story2)
                }
                true
            }
            .assertComplete()
    }

    @Test
    fun test_whenDeletingAllItems_thenComplete() {
        topStoriesDao.insert(story1, story2)
            .blockingAwait()
        Completable.fromAction {
            topStoriesDao.deleteAll()
        }
            .test()
            .assertComplete()
    }


    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        try {
            database = mainDataBase(getApplicationContext())
        } catch (e: Exception) {
            Timber.i(e.message!!)
        }

        topStoriesDao = database.topStoriesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        private val source1 = SourceEntity("source1", "Source1", "source_url1")
        private val source2 = SourceEntity("source2", "Source2", "source_url2")

        private val story1 = TopStoryEntity("url1", "title1", sourceEntity = source1, "topic1")
        private val story2 = TopStoryEntity("url2", "title2", sourceEntity = source2, "topic2")

    }

}