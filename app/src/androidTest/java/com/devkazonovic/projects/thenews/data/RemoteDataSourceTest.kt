package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory.googleNewsClientForTest
import com.devkazonovic.projects.thenews.data.local.Topics
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: RemoteDataSource

    @Test
    fun test_getTopStories() {
        dataSource.getRemoteTopStories("US:en")
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
        dataSource.getRemoteTopicStories(
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
        dataSource = RemoteDataSource(googleNewsClientForTest)
    }

    companion object {
    }
}