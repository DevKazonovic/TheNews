package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.T_SavedStory
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Source
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import javax.inject.Inject

class StoryDataModelEntity @Inject constructor() : Mapper<Story, T_Story> {
    override fun map(input: Story?): T_Story {
        return input?.let {
            T_Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = SourceDataModelEntity().map(it.source)
            )
        } ?: T_Story.EMPTY
    }
}

class SavedStoryDataModelEntity @Inject constructor() : Mapper<Story, T_SavedStory> {
    override fun map(input: Story?): T_SavedStory {
        return input?.let {
            T_SavedStory(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = SourceDataModelEntity().map(it.source)
            )
        } ?: T_SavedStory.EMPTY
    }
}

class SourceDataModelEntity @Inject constructor() : Mapper<Source, T_Source> {
    override fun map(input: Source?): T_Source {
        return input?.let {
            T_Source(it.id, it.name, it.url)
        } ?: T_Source.NO_SOURCE
    }
}
