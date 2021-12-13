package com.devkazonovic.projects.thenews.data.remote.help

import com.devkazonovic.projects.thenews.data.remote.help.IOUtil.findFiles
import com.devkazonovic.projects.thenews.data.remote.help.IOUtil.readAll
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


object GoogleNewsFeedMock {

    const val ID_TECH = "CAAqJggKIiBDQkFTRWdvSUwyMHZNRGRqTVhZU0FtVnVHZ0pWVXlnQVAB"

    val mockDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/rss?ceid=US:en" -> {
                    MockResponse().apply {
                        setResponseCode(200)
                        setBody(readAll(findFiles("topstories.xml")))
                    }
                }
                "/rss/topics/$ID_TECH" -> {
                    MockResponse().apply {
                        setResponseCode(200)
                        setBody(readAll(findFiles("topstories.xml")))
                    }
                }

                "/rss/search?q=tech" -> {
                    MockResponse().apply {
                        setResponseCode(200)
                        setBody(readAll(findFiles("topstories.xml")))
                    }
                }
                else -> {
                    MockResponse().apply {
                        setResponseCode(400)
                    }
                }
            }
        }
    }


}