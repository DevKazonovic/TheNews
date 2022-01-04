package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.AndroidTestFactory.mapper
import com.devkazonovic.projects.thenews.data.local.Topics
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.GoogleNewsClient
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

    @Before
    fun setUp() {
        dataSource = RemoteDataSource(
            AndroidTestFactory.googleNewsRssRetrofit(
                AndroidTestFactory.URL_GOOGLE_NEWS_FEED,
                Schedulers.trampoline()
            )
                .create(GoogleNewsClient::class.java),
            mapper
        )
    }

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
        dataSource.searchByKeyword("covid", "US:en")
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
        dataSource.getTopicStories(Topics.getTopics(getApplicationContext())[0].id, "US:en")
            .observeOn(Schedulers.trampoline())
            .test()
            .assertValue {
                assertThat(it).isNotNull()
                assertThat(it).isNotEmpty()
                true
            }
    }


}