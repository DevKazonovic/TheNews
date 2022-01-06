package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.dateFormatter_clock_ut_date2022_01_2_time0_0_0
import com.devkazonovic.projects.thenews.AndroidTestFactory.domainModelMappers
import com.devkazonovic.projects.thenews.AndroidTestFactory.entityMappers
import com.devkazonovic.projects.thenews.AndroidTestFactory.googleNewsClientForTest
import com.devkazonovic.projects.thenews.AndroidTestFactory.pojoMappers
import com.devkazonovic.projects.thenews.data.local.Topics
import com.devkazonovic.projects.thenews.domain.mapper.MapperFactory
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: RemoteDataSource


    @Test
    fun test_getTopStories() {
        dataSource.getTopStories("US:en")
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue {
                assertThat(it).isNotNull()
                assertThat(it).isNotEmpty()
                true
            }
    }


    @Test
    fun test_searchStoriesByKeyword() {
        dataSource.searchByKeyword(keyword = "covid", ceid = "US:en")
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue {
                assertThat(it).isNotNull()
                assertThat(it).isNotEmpty()
                true
            }
    }

    @Test
    fun test_getStoriesByTopic() {
        dataSource.getTopicStories(
            ceid = "US:en",
            topicId = Topics.getTopics(getApplicationContext())[0].id
        )
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue {
                assertThat(it).isNotNull()
                assertThat(it).isNotEmpty()
                true
            }
    }

    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        val uniqueGenerator = mock<UniqueGenerator>()
        whenever(uniqueGenerator.createSourceId(anyString())).thenReturn("sourceId")
        val mappers = MapperFactory(
            pojoMappers(dateFormatter_clock_ut_date2022_01_2_time0_0_0, uniqueGenerator),
            entityMappers(dateFormatter_clock_ut_date2022_01_2_time0_0_0),
            domainModelMappers()
        )
        dataSource = RemoteDataSource(googleNewsClientForTest, mappers)
    }

    companion object {
        private const val sourceId = "sourceId"
    }
}