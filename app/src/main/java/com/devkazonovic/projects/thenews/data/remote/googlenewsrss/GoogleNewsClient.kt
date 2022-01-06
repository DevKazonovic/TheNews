package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TOPIC_ID = "TOPIC_ID"

interface GoogleNewsClient {

    companion object {
        val DEFAULT_LANGUAGE_ZONE = LanguageZone.DEFAULT
        val DEFAULT_ZONE = DEFAULT_LANGUAGE_ZONE.zone
        val DEFAULT_LANGUAGE = DEFAULT_LANGUAGE_ZONE.language
        val DEFAULT_CEID = DEFAULT_LANGUAGE_ZONE.getCeId()
    }

    @GET("/rss")
    fun getTopStories(
        @Query(value = "hl", encoded = true) language: String = DEFAULT_LANGUAGE,
        @Query(value = "gl", encoded = true) country: String = DEFAULT_ZONE,
        @Query(value = "ceid", encoded = true) languageAndCountry: String = DEFAULT_CEID
    ): Single<RSS>

    @GET("/rss/topics/{$TOPIC_ID}")
    fun getTopicStories(
        @Path(TOPIC_ID, encoded = true) topicId: String,
        @Query(value = "hl", encoded = true) language: String = DEFAULT_LANGUAGE,
        @Query(value = "gl", encoded = true) country: String = DEFAULT_ZONE,
        @Query(value = "ceid", encoded = true) languageAndCountry: String = DEFAULT_CEID,
    ): Single<RSS>

    @GET("/rss/search")
    fun search(
        @Query("q", encoded = true) keyword: String,
        @Query(value = "hl", encoded = true) language: String = DEFAULT_LANGUAGE,
        @Query(value = "gl", encoded = true) country: String = DEFAULT_ZONE,
        @Query(value = "ceid", encoded = true) languageAndCountry: String = DEFAULT_CEID,
    ): Single<RSS>
}