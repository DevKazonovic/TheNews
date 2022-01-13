package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import javax.inject.Inject

interface IPojoMappers {
    fun topStoryPojoMapper(): PojoMapper<Item, TopStoryEntity, Story>
    fun sourcePojoMapper(): PojoMapper<ItemSource, SourceEntity, Source>
    fun topicStoryPojoMapper(): PojoMapper<Item, TopicStoryEntity, Story>
}

class PojoMappers @Inject constructor(
    private val sourcePojoMapper: SourcePojoMapper,
    private val topStoryPojoMapper: TopStoryPojoMapper,
    private val topicStoryPojoMapper: TopicStoryPojoMapper
) : IPojoMappers {
    override fun sourcePojoMapper(): PojoMapper<ItemSource, SourceEntity, Source> =
        sourcePojoMapper

    override fun topStoryPojoMapper(): PojoMapper<Item, TopStoryEntity, Story> =
        topStoryPojoMapper

    override fun topicStoryPojoMapper(): PojoMapper<Item, TopicStoryEntity, Story> =
        topicStoryPojoMapper
}


class TopStoryPojoMapper @Inject constructor(
    private val sourcePojoMapper: SourcePojoMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : PojoMapper<Item, TopStoryEntity, Story> {
    override fun toEntity(input: Item?): TopStoryEntity {
        return if (input?.link == null) {
            TopStoryEntity.EMPTY
        } else {
            TopStoryEntity(
                url = input.link,
                sourceEntity = sourcePojoMapper.toEntity(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate ?: "",
            )
        }
    }

    override fun toDomainModel(input: Item?): Story {
        return if (input?.link == null) {
            Story.EMPTY
        } else {
            Story(
                url = input.link,
                source = sourcePojoMapper.toDomainModel(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate ?: "",
                publishDateFormat = input.pubDate?.let {
                    dateTimeFormatter.calcTimePassed(it)
                } ?: Pair(0, Ago.NON)
            )
        }
    }
}

class TopicStoryPojoMapper @Inject constructor(
    private val sourcePojoMapper: SourcePojoMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : PojoMapper<Item, TopicStoryEntity, Story> {
    override fun toEntity(input: Item?): TopicStoryEntity {
        return if (input?.link == null) {
            TopicStoryEntity.EMPTY
        } else {
            TopicStoryEntity(
                url = input.link,
                sourceEntity = sourcePojoMapper.toEntity(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate ?: "",
            )
        }
    }

    override fun toDomainModel(input: Item?): Story {
        return if (input?.link == null) {
            Story.EMPTY
        } else {
            Story(
                url = input.link,
                source = sourcePojoMapper.toDomainModel(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate ?: "",
                publishDateFormat = input.pubDate?.let {
                    dateTimeFormatter.calcTimePassed(it)
                } ?: Pair(0, Ago.NON)
            )
        }
    }
}

class SourcePojoMapper @Inject constructor(
    private val uniqueGenerator: UniqueGenerator
) : PojoMapper<ItemSource, SourceEntity, Source> {

    override fun toEntity(input: ItemSource?): SourceEntity {
        return input?.let {
            SourceEntity(
                id = uniqueGenerator.createSourceId(it.text),
                name = it.text ?: "",
                url = it.url ?: ""
            )
        } ?: SourceEntity.NO_SOURCE_ENTITY
    }

    override fun toDomainModel(input: ItemSource?): Source {
        return if (input == null) Source.NO_SOURCE
        else Source(
            id = uniqueGenerator.createSourceId(input.text),
            name = input.text ?: Source.NON_VALUE,
            url = input.url ?: Source.NON_VALUE
        )
    }

}

