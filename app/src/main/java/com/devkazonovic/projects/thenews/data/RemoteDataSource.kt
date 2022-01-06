package com.devkazonovic.projects.thenews.data

import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.GoogleNewsClient
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.RSS
import com.devkazonovic.projects.thenews.domain.mapper.Mappers
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val googleNewsClient: GoogleNewsClient,
    private val mapper: Mappers
) {

    fun getTopStories(ceid: String): Single<List<Story>> {
        val languageZone = LanguageZone.getLanguageZone(ceid)
        return googleNewsClient.getTopStories(
            country = languageZone.first,
            language = languageZone.second,
            languageAndCountry = ceid
        ).map {
            getItemsFromRss(it).map { item ->
                mapper.pojoMappers().storyPojoMapper().toDomainModel(
                    item
                )
            }
        }
    }

    fun getTopicStories(ceid: String, topicId: String): Single<List<Story>> {
        val languageZone = LanguageZone.getLanguageZone(ceid)
        return googleNewsClient.getTopicStories(
            country = languageZone.first,
            language = languageZone.second,
            languageAndCountry = ceid,
            topicId = topicId
        ).map {
            getItemsFromRss(it).map { item ->
                mapper.pojoMappers().storyPojoMapper().toDomainModel(
                    item
                )
            }
        }
    }

    fun searchByKeyword(ceid: String, keyword: String): Single<List<Story>> {
        val languageZone = LanguageZone.getLanguageZone(ceid)
        return googleNewsClient.search(
            country = languageZone.first,
            language = languageZone.second,
            keyword = keyword,
            languageAndCountry = ceid
        ).map {
            getItemsFromRss(it).map { item ->
                mapper.pojoMappers().storyPojoMapper().toDomainModel(
                    item
                )
            }
        }
    }

    private fun getItemsFromRss(rss: RSS): List<Item> {
        return rss.channel?.items ?: listOf()
    }

}