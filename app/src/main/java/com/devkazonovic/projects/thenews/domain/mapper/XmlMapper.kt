package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.T_Source
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import javax.inject.Inject

class StoryXmlDataModel @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter
) : Mapper<Item, Story> {
    override fun map(input: Item?): Story {
        return if (input?.link == null) {
            Story.EMPTY
        } else {
            Story(
                url = input.link,
                source = SourceXmlDataModel().map(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate?:"",
                publishDateFormat = input.pubDate?.let {
                    dateTimeFormatter.howMuchAgo(it)
                }?: Pair(0,Ago.NON)
            )
        }
    }
}

class SourceXmlDataModel @Inject constructor() : Mapper<ItemSource, Source> {
    override fun map(input: ItemSource?): Source {
        return if (input == null) Source.NO_SOURCE
        else Source(
            id = Source.createSourceId(input.text),
            name = input.text ?: Source.NON_VALUE,
            url = input.url ?: Source.NON_VALUE
        )
    }
}

class StoryXmlEntity @Inject constructor() : Mapper<Item, T_Story> {
    override fun map(input: Item?): T_Story {
        return if (input?.link == null) {
            T_Story.EMPTY
        } else {
            T_Story(
                url = input.link,
                source = SourceXmlEntity().map(input.itemSource),
                title = input.title ?: "",
                publishDate = input.pubDate ?: "",
            )
        }
    }
}

class SourceXmlEntity @Inject constructor() : Mapper<ItemSource, T_Source> {
    override fun map(input: ItemSource?): T_Source {
        return input?.let {
            T_Source(
                id = T_Story.createSourceId(it.text),
                name = it.text ?: T_Story.NO_SOURCE_VALUE,
                url = it.url ?: T_Story.NO_SOURCE_VALUE
            )
        } ?: T_Story.NO_SOURCE
    }
}


