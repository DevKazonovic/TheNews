package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.devkazonovic.projects.thenews.testHelp.DateUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.threeten.bp.Clock
import org.threeten.bp.ZoneOffset

class PojoMappersTest {

    private lateinit var uniqueGenerator: UniqueGenerator
    private lateinit var sourcePojoMapper: SourcePojoMapper
    private lateinit var dateTimeFormatter: DateTimeFormatter
    private lateinit var storyPojoMapper: StoryPojoMapper

    @Before
    fun setUp() {
        uniqueGenerator = mock(UniqueGenerator::class.java)
        sourcePojoMapper = SourcePojoMapper(uniqueGenerator)
        dateTimeFormatter = DateTimeFormatter(Clock.fixed(DateUtil.get2022_01_2(), ZoneOffset.UTC))
        storyPojoMapper = StoryPojoMapper(sourcePojoMapper, dateTimeFormatter)

        `when`(uniqueGenerator.createSourceId(any())).thenReturn(sourceId)
    }

    //Source
    @Test
    fun testMap_fromItemSource_toSourceEntity() {
        val fromSource = rssSource

        val actual = sourcePojoMapper.toEntity(fromSource)
        val expected = expectedSourceEntity

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testMap_fromItemSource_toSourceDomainModel() {
        assertThat(sourcePojoMapper.toDomainModel(rssSource)).isEqualTo(expectedSourceDomainModel)
    }
    //Source

    //Story
    @Test
    fun testMap_fromItem_toStoryEntity() {
        assertThat(storyPojoMapper.toEntity(rssStory))
            .isEqualTo(expectedStoryEntity)
    }

    @Test
    fun testMap_fromItem_toStoryDomainModel() {
        assertThat(storyPojoMapper.toDomainModel(rssStory))
            .isEqualTo(expectedStoryDomainModel)
    }
    //Story


    companion object {

        private const val sourceId = "sourceId"
        val rssSource =
            ItemSource(url = "https://abcnews.go.com", text = "ABC News")
        val expectedSourceEntity =
            SourceEntity(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val expectedSourceDomainModel =
            Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")

        val rssStory = Item(
            title = "Test in 2022",
            link = "https://news.google.com/2022",
            pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            itemSource = rssSource
        )

        val expectedStoryEntity = StoryEntity(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            sourceEntity = expectedSourceEntity
        )

        val expectedStoryDomainModel = Story(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = expectedSourceDomainModel,
            publishDateFormat = Pair(1, Ago.DAY)
        )
    }
}
