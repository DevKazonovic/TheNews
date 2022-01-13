package com.devkazonovic.projects.thenews.fake

import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.*
import io.reactivex.rxjava3.core.Single

class FakeGoogleNewsClient : GoogleNewsClient {
    companion object {
        val rssSource = ItemSource(url = "https://abcnews.go.com", text = "ABC News")
        val stories = listOf(
            Item(
                title = "Test in 2022",
                link = "https://news.google.com/topic1",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource
            ),
            Item(
                title = "Test in 2023",
                link = "https://news.google.com/topic2",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource

            )
        )
        val topStories = listOf(
            Item(
                title = "Test in 2022",
                link = "https://news.google.com/2022",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource
            ),
            Item(
                title = "Test in 2023",
                link = "https://news.google.com/2023",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource

            )
        )
        val topic1Stories = listOf(
            Item(
                title = "Test in 2022",
                link = "https://news.google.com/topic1",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource
            ),
            Item(
                title = "Test in 2023",
                link = "https://news.google.com/topic2",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource

            )
        )
        val topic2Stories = listOf(
            Item(
                title = "Test in 2022",
                link = "https://news.google.com/topic1",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource
            ),
            Item(
                title = "Test in 2023",
                link = "https://news.google.com/topic2",
                pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
                itemSource = rssSource

            )
        )
    }


    override fun getTopStories(
        language: String,
        country: String,
        languageAndCountry: String
    ): Single<RSS> {
        return Single.just(RSS(Channel(topStories)))
    }

    override fun getTopicStories(
        topicId: String,
        language: String,
        country: String,
        languageAndCountry: String
    ): Single<RSS> {
        return Single.just(RSS(Channel(topic1Stories)))
    }

    override fun search(
        keyword: String,
        language: String,
        country: String,
        languageAndCountry: String
    ): Single<RSS> {
        return Single.just(
            RSS(Channel(stories.filter { it.title?.contains(keyword) ?: false }))
        )
    }
}