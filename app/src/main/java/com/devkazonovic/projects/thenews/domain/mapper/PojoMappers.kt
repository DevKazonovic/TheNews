package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import javax.inject.Inject

class StoryPojoMapper @Inject constructor(
    private val sourcePojoMapper: SourcePojoMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : PojoMapper<Item, StoryEntity, Story> {
    override fun toEntity(input: Item?): StoryEntity {
        return if (input?.link == null) {
            StoryEntity.EMPTY
        } else {
            StoryEntity(
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
                    dateTimeFormatter.howMuchAgo(it)
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
                name = it.text ?: StoryEntity.NO_SOURCE_VALUE,
                url = it.url ?: StoryEntity.NO_SOURCE_VALUE
            )
        } ?: StoryEntity.NO_SOURCE
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


class PojoMappers @Inject constructor(
    val storyPojoMapper: StoryPojoMapper,
    val sourcePojoMapper: SourcePojoMapper
)