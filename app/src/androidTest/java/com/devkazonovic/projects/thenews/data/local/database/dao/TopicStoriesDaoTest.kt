package com.devkazonovic.projects.thenews.data.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
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
class TopicStoriesDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: MainDataBase
    private lateinit var topicStoriesDao: TopicStoriesDao

    @Test
    fun test_whenInsertingItemsWithDifferentUrl_thenComplete() {

        topicStoriesDao.insert(story1, story2)
            .test()
            .assertComplete()

    }

    @Test
    fun test_whenInsertingItemWithSameUrl_thenCompleteButOnlyInsertOne() {

        topicStoriesDao.insert(story1, story1)
            .test()
            .assertComplete()

        topicStoriesDao.findAll()
            .test()
            .assertValue { stories ->
                assertThat(stories).hasSize(1)
                assertThat(stories).containsExactly(story1)
                true
            }.assertComplete()

    }

    @Test
    fun test_whenDeletingItems_thenComplete() {
        topicStoriesDao.insert(story1, story2)
            .blockingAwait()

        topicStoriesDao.delete(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun test_whenQueryingAllTable_thenReturnAllItems_and_Complete() {

        topicStoriesDao.insert(story1, story2)
            .blockingAwait()


        topicStoriesDao.findAll()
            .test()
            .assertValue { stories ->
                assertThat(stories).hasSize(2)
                true
            }.assertComplete()
    }

    @Test
    fun test_whenQueryingByTopicId_thenReturnItemsWithSameTopicId_and_Complete() {

        topicStoriesDao.insert(story1, story2)
            .blockingAwait()


        topicStoriesDao.findByTopic(story1.topicId)
            .test()
            .assertValue { stories ->
                assertThat(stories).hasSize(1)
                assertThat(stories).containsExactly(story1)
                true
            }.assertComplete()
    }

    @Test
    fun test_whenDeletingAllItems_thenComplete() {
        topicStoriesDao.insert(story1, story2)
            .blockingAwait()

        Completable.fromAction { topicStoriesDao.deleteAll() }
            .test()
            .assertComplete()

    }

    @Test
    fun test_whenDeletingByTopicId_thenDeleteAllItemWithTheSameTopicId_and_Complete() {

        topicStoriesDao.insert(story1, story2)
            .blockingAwait()


        Completable.fromAction { topicStoriesDao.deleteByTopic(story1.topicId) }
            .test()
            .assertComplete()

        topicStoriesDao.findAll()
            .test()
            .assertValue { stories ->
                assertThat(stories).hasSize(1)
                true
            }.assertComplete()

    }

    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        try {
            database = AndroidTestFactory.mainDataBase(ApplicationProvider.getApplicationContext())
        } catch (e: Exception) {
            Timber.i(e.message!!)
        }

        topicStoriesDao = database.topicStoriesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        private val source1 = SourceEntity("source1", "Source1", "source_url1")
        private val source2 = SourceEntity("source2", "Source2", "source_url2")

        val topic1 = "world"
        val topic2 = "science"

        private val story1 =
            TopicStoryEntity("url1", "title1", sourceEntity = source1, topicId = topic1)
        private val story2 =
            TopicStoryEntity("url2", "title2", sourceEntity = source2, topicId = topic2)
    }

}