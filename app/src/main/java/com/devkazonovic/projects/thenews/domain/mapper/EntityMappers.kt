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

interface IEntityMappers {
    fun storyEntityMapper(): EntityMapper<StoryEntity, Story, Item>
    fun sourceEntityMapper(): EntityMapper<SourceEntity, Source, ItemSource>
    fun savedStoryMapper(): EntityMapper<SavedStoryEntity, Story, Item>
}

class EntityMappers @Inject constructor(
    private val storyEntityMapper: StoryEntityMapper,
    private val sourceEntityMapper: SourceEntityMapper,
    private val savedStoryMapper: SavedStoryMapper
) : IEntityMappers {

    override fun storyEntityMapper(): EntityMapper<StoryEntity, Story, Item> = storyEntityMapper

    override fun sourceEntityMapper(): EntityMapper<SourceEntity, Source, ItemSource> =
        sourceEntityMapper

    override fun savedStoryMapper(): EntityMapper<SavedStoryEntity, Story, Item> = savedStoryMapper
}

/**Mappers*/
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
                publishDateFormat = dateTimeFormatter.calcTimePassed(it.publishDate),
                isSaved = it.isReadLater == 1
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
                publishDateFormat = dateTimeFormatter.calcTimePassed(it.publishDate)
            )
        } ?: Story.EMPTY
    }
}

