package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.AndroidTestFactory.dateFormatter_clock_utc_date2022_01_2_time0_0_0
import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.TopStoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class EntityMappersTest {

    private lateinit var sourceEntityMapper: SourceEntityMapper
    private lateinit var storyEntityMapper: StoryEntityMapper

    @Test
    fun test_fromSourceEntity_toSourceDomainModel() {
        assertThat(sourceEntityMapper.toDomainModel(sourceEntity)).apply {
            isEqualTo(expectedSourceDomainModel)
        }
    }

    @Test
    fun test_fromStoryEntity_toStoryDomainModel() {
        assertThat(storyEntityMapper.toDomainModel(storyEntity)).apply {
            isEqualTo(expectedStoryDomainModel)
        }
    }


    @Before
    fun setUp() {
        sourceEntityMapper = SourceEntityMapper()
        storyEntityMapper = StoryEntityMapper(
            sourceEntityMapper,
            dateFormatter_clock_utc_date2022_01_2_time0_0_0
        )
    }

    companion object {
        private const val sourceId = "sourceId"
        val sourceEntity =
            SourceEntity(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val storyEntity = TopStoryEntity(
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