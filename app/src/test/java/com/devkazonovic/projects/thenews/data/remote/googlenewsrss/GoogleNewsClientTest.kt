package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import com.devkazonovic.projects.thenews.AndroidTestFactory.googleNewsRssRetrofit
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class GoogleNewsClientTest {

    private lateinit var googleNewsClient: GoogleNewsClient
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getTopStories() {
        googleNewsClient.getTopStories()
            .test()
            .assertValue { rss ->
                assertThat(rss).isNotNull()
                assertThat(rss.channel).isNotNull()
                assertThat(rss.channel?.lastBuildDate).isEqualTo(lastBuildDate)
                assertThat(rss.channel?.items).hasSize(2)
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
                assertThat(rss.channel?.lastBuildDate).isEqualTo(lastBuildDate)
                assertThat(rss.channel?.items).hasSize(2)
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
                assertThat(rss.channel?.items).hasSize(2)
                assertThat(rss.channel?.items).contains(item)
                true
            }
    }

    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = GoogleNewsFeedMock.mockDispatcher
        mockWebServer.start()
        googleNewsClient = googleNewsRssRetrofit(
            mockWebServer.url("/").toUrl().toString(),
            Schedulers.trampoline()
        ).create(GoogleNewsClient::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //--------Dummy Object---------//
    companion object {
        private val lastBuildDate = "San, 01 Jan 2022 08:00:00 GMT"

        private val item = Item(
            "Aljazeera Article Title",
            "https://www.aljazeera.com/sports/2022/1/5/path",
            "San, 01 Jan 2022 05:00:00 GMT",
            ItemSource(
                "https://www.aljazeera.com/",
                "Aljazeera"
            )
        )
    }
}