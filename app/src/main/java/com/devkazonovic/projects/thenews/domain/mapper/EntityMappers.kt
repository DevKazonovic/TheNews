package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import javax.inject.Inject

class StoryEntityMapper @Inject constructor(
    private val sourceEntityMapper: SourceEntityMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : EntityMapper<StoryEntity, Story, Item> {
    override fun toPojo(input: StoryEntity?): Item = Item()
    override fun toDomainModel(input: StoryEntity?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = sourceEntityMapper.toDomainModel(it.sourceEntity),
                publishDateFormat = dateTimeFormatter.howMuchAgo(it.publishDate)
            )
        } ?: Story.EMPTY
    }
}

class SourceEntityMapper @Inject constructor() : EntityMapper<SourceEntity, Source, ItemSource> {
    override fun toPojo(input: SourceEntity?): ItemSource {
        TODO("Not yet implemented")
    }

    override fun toDomainModel(input: SourceEntity?): Source {
        return input?.let {
            Source(it.id, it.name, it.url)
        } ?: Source.NO_SOURCE
    }
}

class SavedStoryMapper @Inject constructor(
    private val sourceEntityMapper: SourceEntityMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : EntityMapper<SavedStoryEntity, Story, Item> {
    override fun toPojo(input: SavedStoryEntity?): Item = Item()

    override fun toDomainModel(input: SavedStoryEntity?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = sourceEntityMapper.toDomainModel(it.sourceEntity),
                publishDateFormat = dateTimeFormatter.howMuchAgo(it.publishDate)
            )
        } ?: Story.EMPTY
    }
}

class EntityMappers @Inject constructor(
    val storyEntityMapper: StoryEntityMapper,
    val sourceEntityMapper: SourceEntityMapper,
    val savedStoryMapper: SavedStoryMapper
)