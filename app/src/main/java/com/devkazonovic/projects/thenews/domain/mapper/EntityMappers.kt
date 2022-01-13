package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import javax.inject.Inject

interface IEntityMappers {
    fun sourceEntityMapper(): EntityMapper<SourceEntity, Source, ItemSource>
    fun storyEntityMapper(): EntityMapper<TopStoryEntity, Story, Item>
    fun savedStoryMapper(): EntityMapper<SavedStoryEntity, Story, Item>
    fun topicStoryMapper(): EntityMapper<TopicStoryEntity, Story, Item>

}

class EntityMappers @Inject constructor(
    private val sourceEntityMapper: SourceEntityMapper,
    private val storyEntityMapper: StoryEntityMapper,
    private val savedStoryMapper: SavedStoryMapper,
    private val topicStoryMapper: TopicStoryMapper
) : IEntityMappers {
    override fun sourceEntityMapper(): EntityMapper<SourceEntity, Source, ItemSource> =
        sourceEntityMapper

    override fun storyEntityMapper(): EntityMapper<TopStoryEntity, Story, Item> = storyEntityMapper
    override fun savedStoryMapper(): EntityMapper<SavedStoryEntity, Story, Item> = savedStoryMapper
    override fun topicStoryMapper(): EntityMapper<TopicStoryEntity, Story, Item> = topicStoryMapper
}

class SourceEntityMapper @Inject constructor() : EntityMapper<SourceEntity, Source, ItemSource> {
    override fun toPojo(input: SourceEntity?): ItemSource = ItemSource()
    override fun toDomainModel(input: SourceEntity?): Source {
        return input?.let {
            Source(it.id, it.name, it.url)
        } ?: Source.NO_SOURCE
    }
}

class StoryEntityMapper @Inject constructor(
    private val sourceEntityMapper: SourceEntityMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : EntityMapper<TopStoryEntity, Story, Item> {
    override fun toPojo(input: TopStoryEntity?): Item = Item()
    override fun toDomainModel(input: TopStoryEntity?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = sourceEntityMapper.toDomainModel(it.sourceEntity),
                publishDateFormat = dateTimeFormatter.calcTimePassed(it.publishDate)
            )
        } ?: Story.EMPTY
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
                publishDateFormat = dateTimeFormatter.calcTimePassed(it.publishDate)
            )
        } ?: Story.EMPTY
    }
}

class TopicStoryMapper @Inject constructor(
    private val sourceEntityMapper: SourceEntityMapper,
    private val dateTimeFormatter: DateTimeFormatter
) : EntityMapper<TopicStoryEntity, Story, Item> {
    override fun toPojo(input: TopicStoryEntity?): Item = Item()
    override fun toDomainModel(input: TopicStoryEntity?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = sourceEntityMapper.toDomainModel(it.sourceEntity),
                publishDateFormat = dateTimeFormatter.calcTimePassed(it.publishDate)
            )
        } ?: Story.EMPTY
    }
}

