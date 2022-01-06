package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.data.local.database.entity.SourceEntity
import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DomainModelMappersTest {

    private lateinit var sourceModelMapper: SourceModelMapper
    private lateinit var storyModelMapper: StoryModelMapper

    @Test
    fun test_fromSourceDomainModel_toSourceEntity() {
        assertThat(sourceModelMapper.toEntity(sourceDomainModel)).apply {
            isEqualTo(expectedSourceEntity)
        }
    }

    @Test
    fun test_fromStoryDomainModel_toStoryEntity() {
        assertThat(storyModelMapper.toEntity(storyDomainModel)).apply {
            isEqualTo(expectedStoryEntity)
        }
    }


    @Before
    fun setUp() {
        sourceModelMapper = SourceModelMapper()
        storyModelMapper = StoryModelMapper(sourceModelMapper)
    }

    companion object {
        private const val sourceId = "sourceId"
        val sourceDomainModel =
            Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")

        val storyDomainModel = Story(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = EntityMappersTest.expectedSourceDomainModel,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val expectedSourceEntity =
            SourceEntity(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")

        val expectedStoryEntity = StoryEntity(
            url = "https://news.google.com/2022",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            sourceEntity = EntityMappersTest.sourceEntity
        )

        val expectedSourceRss =
            ItemSource(url = "https://abcnews.go.com", text = "ABC News")

        val expectedStoryRss = Item(
            title = "Test in 2022",
            link = "https://news.google.com/2022",
            pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            itemSource = EntityMappersTest.expectedSourceRss
        )
    }
}