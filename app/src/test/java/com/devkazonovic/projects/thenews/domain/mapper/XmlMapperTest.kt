package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.UnitTestFactory
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Clock

class XmlMapperTest {

    private lateinit var mappers: Mappers

    @Before
    fun setUp() {
        mappers = UnitTestFactory.mapper
    }

    @Test
    fun fromItemSourceToSource() {
        val itemSource = ItemSource("https://domain.com", "source")
        val expected = Source(id = "source_123", url = "https://domain.com", name = "source")

        assertThat(mappers.sourceXmlDataModel().map(itemSource)).apply {
            isEqualTo(expected)
        }
    }

    @Test
    fun fromItemToStory() {
        val item = Item(
            title = "Title",
            link = "https://domain.com",
            pubDate = "2021-12-15T06:20:57Z",
            itemSource = ItemSource("https://domain.com", "source")
        )

        val expected = Story(
            url = "https://domain.com",
            title = "Title",
            publishDate = "2021-12-15T06:20:57Z",
            source = Source(id = "source_123", url = "https://domain.com", name = "source"),
        )

        assertThat(mappers.storyXmlDataModel().map(item)).apply {
            isEqualTo(expected)
        }

    }

}