package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SavedStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopicStoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import javax.inject.Inject

interface IDomainModelMappers {
    fun topicStoryModelMapper(): DomainModelMapper<Story, TopicStoryEntity, Item>
    fun topStoryModelMapper(): DomainModelMapper<Story, TopStoryEntity, Item>
    fun savedStoryModelMapper(): DomainModelMapper<Story, SavedStoryEntity, Item>
    fun sourceModelMapper(): DomainModelMapper<Source, SourceEntity, ItemSource>
}

class DomainModelMappers @Inject constructor(
    private val topStoryModelMapper: TopStoryModelMapper,
    private val savedStoryModelMapper: SavedStoryModelMapper,
    private val topicStoryModelMapper: TopicStoryModelMapper,
    private val sourceModelMapper: SourceModelMapper
) : IDomainModelMappers {
    override fun topStoryModelMapper(): DomainModelMapper<Story, TopStoryEntity, Item> =
        topStoryModelMapper

    override fun savedStoryModelMapper(): DomainModelMapper<Story, SavedStoryEntity, Item> =
        savedStoryModelMapper

    override fun sourceModelMapper(): DomainModelMapper<Source, SourceEntity, ItemSource> =
        sourceModelMapper

    override fun topicStoryModelMapper(): DomainModelMapper<Story, TopicStoryEntity, Item> =
        topicStoryModelMapper
}

class TopicStoryModelMapper @Inject constructor(
    private val sourceModelMapper: SourceModelMapper
) : DomainModelMapper<Story, TopicStoryEntity, Item> {
    override fun toPojo(input: Story?): Item = Item()
    override fun toEntity(input: Story?): TopicStoryEntity {
        return input?.let {
            TopicStoryEntity(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                sourceEntity = sourceModelMapper.toEntity(it.source),
            )
        } ?: TopicStoryEntity.EMPTY
    }
}

class TopStoryModelMapper @Inject constructor(
    private val sourceModelMapper: SourceModelMapper
) : DomainModelMapper<Story, TopStoryEntity, Item> {
    override fun toPojo(input: Story?): Item = Item()
    override fun toEntity(input: Story?): TopStoryEntity {
        return input?.let {
            TopStoryEntity(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                sourceEntity = sourceModelMapper.toEntity(it.source),
            )
        } ?: TopStoryEntity.EMPTY
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
        } ?: SourceEntity.NO_SOURCE_ENTITY
    }
}

