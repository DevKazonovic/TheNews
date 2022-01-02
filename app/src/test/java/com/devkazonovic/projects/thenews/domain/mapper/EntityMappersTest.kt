package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.testHelp.DateUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.ZoneOffset

class EntityMappersTest {

    private lateinit var sourceEntityMapper: SourceEntityMapper
    private lateinit var storyEntityMapper: StoryEntityMapper
    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Before
    fun setUp() {
        dateTimeFormatter = DateTimeFormatter(
            Clock.fixed(DateUtil.get2022_01_2(), ZoneOffset.UTC)
        )
        sourceEntityMapper = SourceEntityMapper()
        storyEntityMapper = StoryEntityMapper(
            sourceEntityMapper,
            dateTimeFormatter
        )
    }

    @Test
    fun testMap_fromSourceEntity_toSourceDomainModel() {
        assertThat(sourceEntityMapper.toDomainModel(sourceEntity)).apply {
            isEqualTo(expectedSourceDomainModel)
        }
    }

    @Test
    fun testMap_fromStoryEntity_toStoryDomainModel() {
        assertThat(storyEntityMapper.toDomainModel(storyEntity)).apply {
            isEqualTo(expectedStoryDomainModel)
        }
    }

    @Test
    fun testMap_fromSourceEntity_toSourceRss() {
        assertThat(sourceEntityMapper.toPojo(sourceEntity)).apply {
            isEqualTo(expectedSourceRss)
        }
    }

    @Test
    fun testMap_fromStoryEntity_toStoryRss() {
        assertThat(storyEntityMapper.toPojo(storyEntity)).apply {
            isEqualTo(expectedStoryRss)
        }
    }

    companion object {
        private const val sourceId = "sourceId"
        val sourceEntity =
            SourceEntity(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val storyEntity = StoryEntity(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            sourceEntity = sourceEntity
        )

        val expectedSourceDomainModel =
            Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val expectedStoryDomainModel = Story(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = expectedSourceDomainModel,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val expectedSourceRss =
            ItemSource(url = "https://abcnews.go.com", text = "ABC News")
        val expectedStoryRss = Item(
            title = "Test in 2022",
            link = "https://news.google.com/2022",
            pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            itemSource = expectedSourceRss
        )
    }

}