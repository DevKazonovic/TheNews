package com.devkazonovic.projects.thenews

import android.content.Context
import androidx.room.Room
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.GoogleNewsClient
import com.devkazonovic.projects.thenews.domain.mapper.*
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import com.devkazonovic.projects.thenews.util.DateUtil
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Clock
import org.threeten.bp.ZoneOffset
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import timber.log.Timber

object AndroidTestFactory {

    class TestRxSchedulersFactory : RxSchedulers {
        override fun ioScheduler(): Scheduler = Schedulers.trampoline()

        override fun computationScheduler(): Scheduler = Schedulers.trampoline()

        override fun uiScheduler(): Scheduler = Schedulers.trampoline()
    }

    private const val URL_GOOGLE_NEWS_FEED = "https://news.google.com/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor { println(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(HttpLoggingInterceptor { Timber.d(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

    fun googleNewsRssRetrofit(baseUrl: String, ioScheduler: Scheduler = Schedulers.io())
            : Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(okHttpClient)
        addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(ioScheduler))
        addConverterFactory(SimpleXmlConverterFactory.create())
    }.build()

    val googleNewsClientForTest: GoogleNewsClient =
        googleNewsRssRetrofit(URL_GOOGLE_NEWS_FEED, Schedulers.trampoline())
            .create(GoogleNewsClient::class.java)

    fun mainDataBase(context: Context): MainDataBase {
        return Room.inMemoryDatabaseBuilder(context, MainDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    val dateFormatter_clock_utc_date2022_01_2_time0_0_0 =
        DateTimeFormatter(Clock.fixed(DateUtil.utc_date2022_01_2_time0_0_0(), ZoneOffset.UTC))

    fun domainModelMappers(): DomainModelMappers {
        val sourceModelMapper = SourceModelMapper()
        val topStoryModelMapper = TopStoryModelMapper(sourceModelMapper)
        val savedStoryModelMapper = SavedStoryModelMapper(sourceModelMapper)
        val topicStoryModelMapper = TopicStoryModelMapper(sourceModelMapper)
        return DomainModelMappers(
            topStoryModelMapper,
            savedStoryModelMapper,
            topicStoryModelMapper,
            sourceModelMapper
        )
    }

    fun entityMappers(dateTimeFormatter: DateTimeFormatter): EntityMappers {
        val sourceEntityMapper = SourceEntityMapper()
        val storyEntityMapper = StoryEntityMapper(sourceEntityMapper, dateTimeFormatter)
        val savedStoryEntityMapper = SavedStoryMapper(sourceEntityMapper, dateTimeFormatter)
        val topicStoryMapper = TopicStoryMapper(sourceEntityMapper, dateTimeFormatter)
        return EntityMappers(
            sourceEntityMapper,
            storyEntityMapper,
            savedStoryEntityMapper,
            topicStoryMapper
        )
    }

    fun pojoMappers(
        dateTimeFormatter: DateTimeFormatter,
        uniqueGenerator: UniqueGenerator
    ): PojoMappers {
        val sourcePojoMapper = SourcePojoMapper(uniqueGenerator)
        val storyPojoMapper = TopStoryPojoMapper(sourcePojoMapper, dateTimeFormatter)
        val topicStoryPojoMapper = TopicStoryPojoMapper(sourcePojoMapper, dateTimeFormatter)
        return PojoMappers(sourcePojoMapper, storyPojoMapper, topicStoryPojoMapper)
    }

}