package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.AndroidTestFactory.dateFormatter_clock_utc_date2022_01_2_time0_0_0
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PojoMappersTest {

    private lateinit var sourcePojoMapper: SourcePojoMapper
    private lateinit var topStoryPojoMapper: TopStoryPojoMapper

    @Test
    fun test_fromItemSource_toSourceEntity() {
        val fromSource = rssSource

        val actual = sourcePojoMapper.toEntity(fromSource)
        val expected = expectedSourceEntity

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun test_fromItemSource_toSourceDomainModel() {
        assertThat(sourcePojoMapper.toDomainModel(rssSource)).isEqualTo(expectedSourceDomainModel)
    }

    @Test
    fun test_fromItem_toStoryEntity() {
        assertThat(topStoryPojoMapper.toEntity(rssStory))
            .isEqualTo(expectedStoryEntity)
    }

    @Test
    fun test_fromItem_toStoryDomainModel() {
        assertThat(topStoryPojoMapper.toDomainModel(rssStory))
            .isEqualTo(expectedStoryDomainModel)
    }

    @Before
    fun setUp() {
        val uniqueGenerator = mock(UniqueGenerator::class.java)
        sourcePojoMapper = SourcePojoMapper(uniqueGenerator)
        topStoryPojoMapper =
            TopStoryPojoMapper(sourcePojoMapper, dateFormatter_clock_utc_date2022_01_2_time0_0_0)

        whenever(uniqueGenerator.createSourceId(anyString())).thenReturn(sourceId)
    }

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
        val expectedStoryEntity = TopStoryEntity(
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
