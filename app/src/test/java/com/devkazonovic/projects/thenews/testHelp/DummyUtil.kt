package com.devkazonovic.projects.thenews.testHelp

import com.devkazonovic.projects.thenews.data.local.database.entity.T_Source
import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story

object DummyUtil {

    val entitySource1 = T_Source("source1", "Source1", "source_url1")
    val entitySource2 = T_Source("source2", "Source2", "source_url2")

    val entityStory1 = T_Story("url1", "title1", source = entitySource1, "topic1")
    val entityStory2 = T_Story("url2", "title2", source = entitySource2, "topic2")

    val dataModelSource1 = Source("source1", "Source1", "source_url1")
    val dataModelSource2 = Source("source2", "Source2", "source_url2")

    val dataModelStory1 = Story(url = "url1", title = "title1", source = dataModelSource1)
    val dataModelStory2 = Story(url = "url2", title = "title2", source = dataModelSource2)

}