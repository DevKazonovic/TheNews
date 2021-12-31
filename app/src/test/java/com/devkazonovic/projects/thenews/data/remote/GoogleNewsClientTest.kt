package com.devkazonovic.projects.thenews.data.remote

import com.devkazonovic.projects.thenews.UnitTestFactory
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.GoogleNewsClient
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.testHelp.GoogleNewsFeedMock
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class GoogleNewsClientTest {

    private lateinit var googleNewsClient: GoogleNewsClient
    private lateinit var mockWebServer: MockWebServer
    private val item = Item("Title1", "link1", "date1", ItemSource("url1", "source1"))

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = GoogleNewsFeedMock.mockDispatcher
        mockWebServer.start()
        googleNewsClient = UnitTestFactory.api(
            mockWebServer.url("/").toUrl().toString(),
            Schedulers.trampoline()
        ).create(GoogleNewsClient::class.java)
    }

    @Test
    fun getTopStories() {
        googleNewsClient.getTopStories()
            .test()
            .assertValue { rss ->
                assertThat(rss).isNotNull()
                assertThat(rss.channel).isNotNull()
                assertThat(rss.channel?.items).hasSize(3)
                assertThat(rss.channel?.items).contains(item)
                true
            }
    }

    @Test
    fun getTopicStories() {
        googleNewsClient.getTopicStories(topicId = GoogleNewsFeedMock.ID_TECH)
            .test()
            .assertValue { rss ->
                assertThat(rss).isNotNull()
                assertThat(rss.channel).isNotNull()
                assertThat(rss.channel?.items).hasSize(3)
                assertThat(rss.channel?.items).contains(item)
                true
            }
    }

    @Test
    fun getSearch() {
        googleNewsClient.search(keyword = "tech")
            .test()
            .assertValue { rss ->
                assertThat(rss).isNotNull()
                assertThat(rss.channel).isNotNull()
                assertThat(rss.channel?.items).hasSize(3)
                assertThat(rss.channel?.items).contains(item)
                true
            }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}