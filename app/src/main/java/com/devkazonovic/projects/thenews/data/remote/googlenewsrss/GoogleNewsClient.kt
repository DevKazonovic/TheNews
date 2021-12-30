package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import com.devkazonovic.projects.thenews.data.remote.RSS
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOPIC_ID = "TOPIC_ID"

interface GoogleNewsClient {

    @GET("/rss")
    fun getTopStories(
        @Query(value = "hl", encoded = true) language: String = "en",
        @Query(value = "gl", encoded = true) country: String = "US",
        @Query(value = "ceid", encoded = true) languageAndCountry: String = "US:en"
    ): Single<RSS>

    @GET("/rss/topics/{$TOPIC_ID}")
    fun getTopicStories(
        @Path(TOPIC_ID, encoded = true) topicId: String,
        @Query(value = "hl", encoded = true) language: String = "en",
        @Query(value = "gl", encoded = true) country: String = "US",
        @Query(value = "ceid", encoded = true) languageAndCountry: String = "US:en",
    ): Single<RSS>

    @GET("/rss/search")
    fun search(
        @Query(value = "hl", encoded = true) language: String = "en",
        @Query(value = "gl", encoded = true) country: String = "US",
        @Query(value = "ceid", encoded = true) languageAndCountry: String = "US:en",
        @Query("q", encoded = true) keyword: String
    ): Single<RSS>
}