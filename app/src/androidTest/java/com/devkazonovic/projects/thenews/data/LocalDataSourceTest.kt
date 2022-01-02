package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.DummyUtil.dataModelStory1
import com.devkazonovic.projects.thenews.DummyUtil.dataModelStory2
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import com.devkazonovic.projects.thenews.domain.mapper.PojoMappers
import com.devkazonovic.projects.thenews.domain.mapper.SourcePojoMapper
import com.devkazonovic.projects.thenews.domain.mapper.StoryPojoMapper
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.threeten.bp.Clock

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mappers: Mappers
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        mappers = mock(Mappers::class.java)
        `when`(mappers.pojoMappers()).thenReturn(
            PojoMappers(
                StoryPojoMapper(
                    SourcePojoMapper(UniqueGenerator()),
                    DateTimeFormatter(Clock.systemUTC())
                ), SourcePojoMapper(UniqueGenerator())
            )
        )
        localDataSource = LocalDataSource(
            AndroidTestFactory.getMainDataBase(ApplicationProvider.getApplicationContext()),
            mappers
        )
    }

    @Test
    fun test_saveStoryToReadLater() {
        localDataSource.saveStoryToReadLater(dataModelStory1)
            .test()
            .assertComplete()
    }

    @Test
    fun test_deleteStoryFromReadLater() {
        localDataSource.saveStoryToReadLater(dataModelStory1)
            .blockingAwait()
        localDataSource.deleteStoryToReadLater(dataModelStory1)
            .test()
            .assertComplete()
    }

    @Test
    fun test_getSavedStories() {
        localDataSource.saveStoryToReadLater(dataModelStory1)
            .blockingAwait()
        localDataSource.saveStoryToReadLater(dataModelStory2)
            .blockingAwait()

        localDataSource.getStoriesReadLater()
            .test()
            .assertValue {
                assertThat(it).apply {
                    hasSize(2)
                    containsExactly(dataModelStory1, dataModelStory2)
                }
                true
            }
    }
}