package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.T_SavedStory
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Source
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import javax.inject.Inject

class StoryEntityDataModel @Inject constructor() : Mapper<T_Story, Story> {
    override fun map(input: T_Story?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = SourceEntityDataModel().map(it.source)
            )
        } ?: Story.EMPTY
    }
}

class SavedStoryEntityDataModel @Inject constructor() : Mapper<T_SavedStory, Story> {
    override fun map(input: T_SavedStory?): Story {
        return input?.let {
            Story(
                url = it.url,
                title = it.title,
                publishDate = it.publishDate,
                source = SourceEntityDataModel().map(it.source)
            )
        } ?: Story.EMPTY
    }
}

class SourceEntityDataModel @Inject constructor() : Mapper<T_Source, Source> {
    override fun map(input: T_Source?): Source {
        return input?.let {
            Source(it.id, it.name, it.url)
        } ?: Source.NO_SOURCE
    }
}
