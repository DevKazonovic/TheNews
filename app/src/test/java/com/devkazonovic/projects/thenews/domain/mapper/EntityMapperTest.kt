package com.devkazonovic.projects.thenews.domain.mapper

import com.devkazonovic.projects.thenews.testHelp.DummyUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class EntityMapperTest {

    private lateinit var mappers: Mappers

    @Before
    fun setUp() {
        mappers = MapperFactory()
    }

    @Test
    fun mapSourceEntityToDomainModel() {
        val entitySource = DummyUtil.entitySource1
        val dataModelSource = DummyUtil.dataModelSource1

        assertThat(mappers.sourceEntityDataModel().map(entitySource)).isEqualTo(dataModelSource)

    }

    @Test
    fun mapStoryEntityToDomainModel() {
        val entityStory = DummyUtil.entityStory1
        val dataModelStory = DummyUtil.dataModelStory1

        assertThat(mappers.storyEntityDataModel().map(entityStory)).isEqualTo(dataModelStory)

    }
}