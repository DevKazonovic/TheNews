package com.devkazonovic.projects.thenews.domain.mapper

import javax.inject.Inject

interface Mappers {
    fun storyXmlDataModel(): StoryXmlDataModel
    fun sourceXmlDataModel(): SourceXmlDataModel
    fun storyEntityDataModel(): StoryEntityDataModel
    fun savedStoryEntityDataModel(): SavedStoryEntityDataModel
    fun sourceEntityDataModel(): SourceEntityDataModel
    fun storyDataModelEntity(): StoryDataModelEntity
    fun savedStoryDataModelEntity(): SavedStoryDataModelEntity
    fun sourceDataModelEntity(): SourceDataModelEntity

}

class MapperFactory @Inject constructor(
    private val storyXmlDataModel: StoryXmlDataModel,
    private val sourceXmlDataModel: SourceXmlDataModel,
    private val storyEntityDataModel: StoryEntityDataModel,
    private val savedStoryEntityDataModel: SavedStoryEntityDataModel,
    private val sourceEntityDataModel: SourceEntityDataModel,
    private val storyDataModelEntity: StoryDataModelEntity,
    private val savedStoryDataModelEntity: SavedStoryDataModelEntity,
    private val sourceDataModelEntity: SourceDataModelEntity

) : Mappers {
    override fun storyXmlDataModel(): StoryXmlDataModel = storyXmlDataModel
    override fun sourceXmlDataModel(): SourceXmlDataModel = sourceXmlDataModel
    override fun storyEntityDataModel(): StoryEntityDataModel = storyEntityDataModel
    override fun savedStoryEntityDataModel(): SavedStoryEntityDataModel =
        savedStoryEntityDataModel

    override fun sourceEntityDataModel(): SourceEntityDataModel = sourceEntityDataModel
    override fun storyDataModelEntity(): StoryDataModelEntity = storyDataModelEntity
    override fun savedStoryDataModelEntity(): SavedStoryDataModelEntity =
        savedStoryDataModelEntity

    override fun sourceDataModelEntity(): SourceDataModelEntity = sourceDataModelEntity

}