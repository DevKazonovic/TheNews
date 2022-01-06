package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.AndroidTestFactory.dateFormatter_clock_ut_date2022_01_2_time0_0_0
import com.devkazonovic.projects.thenews.AndroidTestFactory.domainModelMappers
import com.devkazonovic.projects.thenews.AndroidTestFactory.entityMappers
import com.devkazonovic.projects.thenews.AndroidTestFactory.pojoMappers
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.domain.mapper.MapperFactory
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.core.Completable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainDataBase: MainDataBase
    private lateinit var localDataSource: LocalDataSource

    @Test
    fun test_saveStoriesToCache() {
        localDataSource.saveStoriesToCache(listOf(story1, story2))
            .test()
            .assertComplete()
        localDataSource.getCachedStories()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                }
                true
            }
            .assertComplete()
    }

    @Test
    fun deleteCachedStories() {
        Completable.fromCallable {
            localDataSource.deleteAllCachedStories()
        }.test()
            .assertComplete()
    }

    @Test
    fun test_saveStoryToReadLater() {
        localDataSource.saveStoryToReadLater(story1)
            .test()
            .assertComplete()
    }

    @Test
    fun test_deleteStoryFromReadLater() {
        localDataSource.saveStoryToReadLater(story1)
            .blockingAwait()
        localDataSource.deleteStoryToReadLater(story1)
            .test()
            .assertComplete()
    }

    @Test
    fun test_getSavedStories() {
        localDataSource.saveStoryToReadLater(story1)
            .blockingAwait()
        localDataSource.saveStoryToReadLater(story2)
            .blockingAwait()

        localDataSource.getStoriesReadLater()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                }
                true
            }
    }

    //--------Setup & Teardown---------//
    @Before
    fun setUp() {

        val uniqueGenerator = mock<UniqueGenerator>()
        whenever(uniqueGenerator.createSourceId(ArgumentMatchers.anyString())).thenReturn(sourceId)
        val mappers = MapperFactory(
            pojoMappers(dateFormatter_clock_ut_date2022_01_2_time0_0_0, uniqueGenerator),
            entityMappers(dateFormatter_clock_ut_date2022_01_2_time0_0_0),
            domainModelMappers()
        )
        mainDataBase = AndroidTestFactory.mainDataBase(getApplicationContext())
        localDataSource = LocalDataSource(mainDataBase, mappers)
    }

    @After
    fun tearDown() {
        mainDataBase.close()
    }

    //--------Dummy Objects---------//
    companion object {
        private const val sourceId = "sourceId"
        val source =
            Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val story1 = Story(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )
        val story2 = Story(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )
    }
}