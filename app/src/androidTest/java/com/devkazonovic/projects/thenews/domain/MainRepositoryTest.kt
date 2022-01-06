package com.devkazonovic.projects.thenews.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.AndroidTestFactory.mainDataBase
import com.devkazonovic.projects.thenews.data.LocalDataSource
import com.devkazonovic.projects.thenews.data.RemoteDataSource
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.Item
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ItemSource
import com.devkazonovic.projects.thenews.domain.mapper.MapperFactory
import com.devkazonovic.projects.thenews.domain.model.*
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.google.common.truth.Truth.assertThat
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class MainRepositoryTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainDataBase: MainDataBase
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: MainRepository

    @Test
    fun whenCacheIsEmptyReloadIsFalse_getStoriesFromRemoteSourceSaveThem_thenReturnCachedStories() {
        whenever(remoteDataSource.getTopStories(anyString())).thenReturn(
            Single.just(
                listOf(
                    story1,
                    story2
                )
            )
        )
        repository.getStories(LanguageZone.DEFAULT.getCeId())
            .test()
            .assertValue {
                assertThat(it).isInstanceOf(Resource.Success::class.java)
                assertThat((it as Resource.Success).data).apply {
                    hasSize(2)
                }
                true
            }.assertComplete()
    }

    @Test
    fun whenCacheIsNotEmptyReloadIsFalse_getStoriesFromRemoteSourceSaveThem_returnCachedStories() {
        localDataSource.saveStoriesToCache(listOf(story3, story4)).blockingAwait()
        whenever(remoteDataSource.getTopStories(anyString())).thenReturn(
            Single.just(listOf(story1, story2))
        )

        repository.getStories(LanguageZone.DEFAULT.getCeId())
            .test()
            .assertValue {
                assertThat(it).isInstanceOf(Resource.Success::class.java)
                assertThat((it as Resource.Success).data).apply {
                    hasSize(2)
                    containsExactly(story3, story4)
                }
                true
            }.assertComplete()
    }

    @Test
    fun whenCacheIsNotEmptyButReloadIsTrue_getStoriesFromRemoteSourceSaveThem_returnCachedStories() {
        localDataSource.saveStoriesToCache(listOf(story3, story4)).blockingAwait()
        whenever(remoteDataSource.getTopStories(anyString())).thenReturn(
            Single.just(
                listOf(
                    story1,
                    story2
                )
            )
        )
        repository.getStories(LanguageZone.DEFAULT.getCeId(), true)
            .test()
            .assertValue {
                assertThat(it).isInstanceOf(Resource.Success::class.java)
                assertThat((it as Resource.Success).data).apply {
                    hasSize(2)
                    containsExactly(story1, story2)
                }
                true
            }.assertComplete()
    }

    //--------Setup & Teardown---------//
    @Before
    fun setUp() {
        val uniqueGenerator = org.mockito.kotlin.mock<UniqueGenerator>()
        whenever(uniqueGenerator.createSourceId(anyString())).thenReturn(sourceId)
        val mappers = MapperFactory(
            AndroidTestFactory.pojoMappers(
                AndroidTestFactory.dateFormatter_clock_ut_date2022_01_2_time0_0_0,
                uniqueGenerator
            ),
            AndroidTestFactory.entityMappers(AndroidTestFactory.dateFormatter_clock_ut_date2022_01_2_time0_0_0),
            AndroidTestFactory.domainModelMappers()
        )
        mainDataBase = mainDataBase(ApplicationProvider.getApplicationContext())
        localDataSource = LocalDataSource(mainDataBase, mappers)
        remoteDataSource = mock(RemoteDataSource::class.java)
        repository = MainRepository(
            remoteDataSource, localDataSource,
            AndroidTestFactory.TestRxSchedulersFactory()
        )
    }

    @After
    fun tearDown() {
        mainDataBase.close()
    }

    //--------Dummy Objects---------//
    companion object {
        private const val sourceId = "sourceId"
        val rssSource = ItemSource(url = "https://abcnews.go.com", text = "ABC News")
        val rssStory1 = Item(
            title = "Test in 2022",
            link = "https://news.google.com/2022",
            pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            itemSource = rssSource
        )

        val rssStory2 = Item(
            title = "Test in 2022",
            link = "https://news.google.com/2021",
            pubDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            itemSource = rssSource
        )

        val source = Source(id = sourceId, name = "ABC News", url = "https://abcnews.go.com")
        val story1 = Story(
            url = "https://news.google.com/1",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val story2 = Story(
            url = "https://news.google.com/2",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val story3 = Story(
            url = "https://news.google.com/3",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )

        val story4 = Story(
            url = "https://news.google.com/4",
            title = "Test in 2022",
            publishDate = "Sat, 01 Jan 2022 05:25:02 GMT",
            source = source,
            publishDateFormat = Pair(1, Ago.DAY)
        )
    }
}