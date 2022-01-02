package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import javax.inject.Inject

class DomainModelMappers @Inject constructor(
    val storyModelMapper: StoryModelMapper,
    val savedStoryModelMapper: SavedStoryModelMapper,
    val sourceModelMapper: SourceModelMapper
)

class StoryModelMapper @Inject constructor(
    private val sourceModelMapper: SourceModelMapper
) : DomainModelMapper<Story, StoryEntity, Item> {
    override fun toPojo(input: Story?): Item = Item()
    override fun toEntity(input: Story?): StoryEntity {
        return input?.let {
            StoryEntity(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                sourceEntity = sourceModelMapper.toEntity(it.source)
            )
        } ?: StoryEntity.EMPTY
    }
}

class SavedStoryModelMapper @Inject constructor(
    private val sourceModelMapper: SourceModelMapper
) : DomainModelMapper<Story, SavedStoryEntity, Item> {
    override fun toPojo(input: Story?): Item = Item()
    override fun toEntity(input: Story?): SavedStoryEntity {
        return input?.let {
            SavedStoryEntity(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                sourceEntity = sourceModelMapper.toEntity(it.source)
            )
        } ?: SavedStoryEntity.EMPTY
    }
}

class SourceModelMapper @Inject constructor(
) : DomainModelMapper<Source, SourceEntity, ItemSource> {
    override fun toPojo(input: Source?): ItemSource = ItemSource()
    override fun toEntity(input: Source?): SourceEntity {
        return input?.let {
            SourceEntity(it.id, it.name, it.url)
        } ?: SourceEntity.NO_SOURCE
    }
}

