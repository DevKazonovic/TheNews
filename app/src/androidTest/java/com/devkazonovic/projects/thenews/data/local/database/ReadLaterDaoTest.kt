package com.devkazonovic.projects.thenews.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.mainDataBase
import com.devkazonovic.projects.thenews.data.local.database.dao.ReadLaterDao
import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import timber.log.Timber


@RunWith(AndroidJUnit4::class)
class ReadLaterDaoTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: MainDataBase
    private lateinit var readLaterDao: ReadLaterDao

    @Test
    fun testInserting_NItems_thenComplete() {
        readLaterDao.insert(story1)
            .test()
            .assertComplete()
        readLaterDao.insert(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun testDeleting_NItems_thenComplete() {
        readLaterDao.insert(story1, story2)
            .blockingAwait()
        readLaterDao.delete(story1, story2)
            .test()
            .assertComplete()
    }

    @Test
    fun testFindAll() {
        readLaterDao.insert(story1, story2)
            .blockingAwait()
        readLaterDao.findAll()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                    containsExactly(story1, story2)
                }
                true
            }
    }

    @Before
    fun setUp() {
        try {
            database = mainDataBase(getApplicationContext())
        } catch (e: Exception) {
            Timber.i(e.message!!)
        }

        readLaterDao = database.readLaterDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        private val source1 = SourceEntity("source1", "Source1", "source_url1")
        private val source2 = SourceEntity("source2", "Source2", "source_url2")

        private val story1 =
            SavedStoryEntity("url1", "title1", sourceEntity = source1, "topic1")
        private val story2 =
            SavedStoryEntity("url2", "title2", sourceEntity = source2, "topic2")
    }
}