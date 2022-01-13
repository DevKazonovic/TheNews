package com.devkazonovic.projects.thenews.domain

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.data.LocalDataSource
import com.devkazonovic.projects.thenews.data.RemoteDataSource
import com.devkazonovic.projects.thenews.data.local.files.LanguageZoneList
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import com.devkazonovic.projects.thenews.domain.mapper.MapperFactory
import com.devkazonovic.projects.thenews.domain.model.Ago
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Source
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.fake.FakeGoogleNewsClient
import com.devkazonovic.projects.thenews.fake.FakeSharedPreferences
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class MainRepositoryTest {
    lateinit var repository: MainRepository


    @Test
    fun test_getTopStories_WHEN_CacheIsEmptyOrReloadTrue_THEN_RequestData_SaveToCache_ReturnDataFromCache() {
        repository.getTopStories()
            .test()
            .assertValue { result ->
                assertThat(result).isInstanceOf(Resource.Success::class.java)
                assertThat((result as Resource.Success).data).hasSize(FakeGoogleNewsClient.topStories.size)
                assertThat(result.data[0].url).isEqualTo(FakeGoogleNewsClient.topStories[0].link)
                true
            }
    }

    @Test
    fun test_getTopStories_WHEN_CacheIsNotEmpty_ReloadFalse_THEN_ReturnDataFromCache() {
        val localTopicStoriesList1 = FakeGoogleNewsClient.topStories
        repository.saveTopStories(localTopicStoriesList1).blockingAwait()
        repository.getTopStories()
            .test()
            .assertValue { result ->
                assertThat(result).isInstanceOf(Resource.Success::class.java)
                assertThat((result as Resource.Success).data).hasSize(localTopicStoriesList1.size)
                assertThat(result.data[0].url).isEqualTo(localTopicStoriesList1[0].link)
                true
            }


    }

    @Test
    fun test_getTopicStories_WHEN_CacheIsEmptyOrReloadTrue_THEN_RequestData_SaveToCache_ReturnDataFromCache() {
        val topic1 = "topic1"
        repository.getTopicStories(topicId = topic1)
            .test()
            .assertValue { result ->
                assertThat(result).isInstanceOf(Resource.Success::class.java)
                assertThat((result as Resource.Success).data).hasSize(FakeGoogleNewsClient.topic1Stories.size)
                assertThat(result.data[0].url).isEqualTo(FakeGoogleNewsClient.topic1Stories[0].link)
                true
            }
    }

    @Test
    fun test_getTopicStories_WHEN_CacheIsNotEmptyAndReloadFalse_THEN_RequestData_SaveToCache_ReturnDataFromCache() {
        val topic1 = "topic1"
        val topic2 = "topic2"
        val localTopicStoriesList1 = FakeGoogleNewsClient.topic1Stories
        val localTopicStoriesList2 = FakeGoogleNewsClient.topic2Stories
        repository.saveTopicStories(localTopicStoriesList1, topic1).blockingAwait()
        repository.saveTopicStories(localTopicStoriesList2, topic2).blockingAwait()
        repository.getTopicStories(topicId = topic1)
            .test()
            .assertValue { result ->
                assertThat(result).isInstanceOf(Resource.Success::class.java)
                assertThat((result as Resource.Success).data).hasSize(localTopicStoriesList1.size)
                assertThat(result.data[0].url).isEqualTo(localTopicStoriesList1[0].link)
                true
            }
    }

    @Test
    fun test_whenChangingLanguageZone_returnTrue() {
        val zoneId = "MA:fr"
        Truth.assertThat(repository.saveLanguageZone(zoneId)).isTrue()
    }

    @Test
    fun test_whenGetLanguageZone_returnCurrentSavedValue() {
        val expected = "MA:fr"
        repository.saveLanguageZone(expected)

        Truth.assertThat(repository.getCurrentLanguageZone()).isEqualTo(expected)
    }

    @Test
    fun test_whenChangingLanguageZone_emitTheNewValueToAllObservers() {
        val expected1 = "MA:fr"
        repository.saveLanguageZone(expected1)

        repository.getLanguageZoneObservable()
            .test()
            .assertValues(expected1)

        val expected2 = "US:en"
        repository.saveLanguageZone(expected2)

        repository.getLanguageZoneObservable()
            .test()
            .assertValues(expected2)

    }

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val dateTimeFormatter =
            AndroidTestFactory.dateFormatter_clock_utc_date2022_01_2_time0_0_0
        val uniqueGenerator: UniqueGenerator = mock()
        whenever(uniqueGenerator.createSourceId(anyString())).thenReturn(sourceId)
        val rxSchedulers = AndroidTestFactory.TestRxSchedulersFactory()

        val localDataSource = LocalDataSource(
            AndroidTestFactory.mainDataBase(context),
            LocalKeyValue(FakeSharedPreferences())
        )
        val remoteDataSource = RemoteDataSource(FakeGoogleNewsClient())
        val mappers = MapperFactory(
            AndroidTestFactory.pojoMappers(dateTimeFormatter, uniqueGenerator),
            AndroidTestFactory.entityMappers(dateTimeFormatter),
            AndroidTestFactory.domainModelMappers()
        )

        repository = MainRepository(
            remoteDataSource,
            localDataSource,
            LanguageZoneList(context),
            rxSchedulers,
            mappers
        )
    }

    companion object {
        val sourceId = "source1"

        val sourceDomainModel =
            Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")

        val storyDomainModel1 = Story(
            url = "https://news.google.com/cached1",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = sourceDomainModel,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val storyDomainModel2 = Story(
            url = "https://news.google.com/cached2",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = sourceDomainModel,
            publishDateFormat = Pair(1, Ago.DAY)
        )
    }

}