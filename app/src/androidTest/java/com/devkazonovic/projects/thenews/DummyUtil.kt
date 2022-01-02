package com.devkazonovic.projects.thenews

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story

object DummyUtil {

    val entitySource1 = SourceEntity("source1", "Source1", "source_url1")
    val entitySource2 = SourceEntity("source2", "Source2", "source_url2")

    val entityStory1 = StoryEntity("url1", "title1", sourceEntity = entitySource1, "topic1")
    val entityStory2 = StoryEntity("url2", "title2", sourceEntity = entitySource2, "topic2")

    val dataModelSource1 = Source("source1", "Source1", "source_url1")
    val dataModelSource2 = Source("source2", "Source2", "source_url2")

    val dataModelStory1 = Story(url = "url1", title = "title1", source = dataModelSource1)
    val dataModelStory2 = Story(url = "url2", title = "title2", source = dataModelSource2)

}