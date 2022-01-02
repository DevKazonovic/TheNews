package com.devkazonovic.projects.thenews.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.getMainDataBase
import com.devkazonovic.projects.thenews.data.local.database.dao.StoriesDao
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import timber.log.Timber

private val source1 = SourceEntity("source1", "Source1", "source_url1")
private val source2 = SourceEntity("source2", "Source2", "source_url2")

private val story1 = StoryEntity("url1", "title1", sourceEntity = source1, "topic1")
private val story2 = StoryEntity("url2", "title2", sourceEntity = source2, "topic2")

@RunWith(AndroidJUnit4::class)
class StoriesDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: MainDataBase

    private lateinit var storiesDao: StoriesDao

    @Before
    fun setUp() {
        try {
            database = getMainDataBase(getApplicationContext())
        } catch (e: Exception) {
            Timber.i(e.message!!)
        }

        storiesDao = database.storiesDao()
    }

    @Test
    fun testInsert() {
        storiesDao.insert(story1, story2).test()
            .assertComplete()
    }

    @Test
    fun testDelete() {
        storiesDao.insert(story1, story2)
            .blockingAwait()

        storiesDao.delete(story1, story2).test()
            .assertComplete()
    }

    @Test
    fun testFinAll() {
        storiesDao.insert(story1, story2)
            .blockingAwait()
        storiesDao.findAll()
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
    fun testFindByTopic() {
        storiesDao.insert(story1, story2)
            .blockingAwait()

        storiesDao.findTopicStories("topic1")
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(1)
                    containsExactly(story1)
                }
                true
            }
            .assertComplete()
    }

    @After
    fun tearDown() {
        database.close()
    }

}