package com.devkazonovic.projects.thenews

import android.content.Context
import androidx.room.Room
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.domain.mapper.*
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import com.devkazonovic.projects.thenews.service.UniqueGenerator
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Clock
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

    const val URL_GOOGLE_NEWS_FEED = "https://news.google.com/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor { println(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(HttpLoggingInterceptor { Timber.d(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

    fun googleNewsRssRetrofit(
        baseUrl: String,
        ioScheduler: Scheduler = Schedulers.io()
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(okHttpClient)
        addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(ioScheduler))
        addConverterFactory(SimpleXmlConverterFactory.create())
    }.build()

    fun mainDataBase(
        context: Context
    ): MainDataBase {
        return Room.inMemoryDatabaseBuilder(context, MainDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    val dateTimeFormatter = DateTimeFormatter(
        Clock.systemUTC()
    )
    val sourcePojoMapper = SourcePojoMapper(UniqueGenerator())
    val storyPojoMapper = StoryPojoMapper(
        sourcePojoMapper, DateTimeFormatter(Clock.systemUTC())
    )
    val sourceEntityMapper = SourceEntityMapper()
    val storyEntityMapper =
        StoryEntityMapper(sourceEntityMapper, DateTimeFormatter(Clock.systemUTC()))
    val savedStoryEntityMapper =
        SavedStoryMapper(sourceEntityMapper, DateTimeFormatter(Clock.systemUTC()))

    val sourceModelMapper = SourceModelMapper()
    val storyModelMapper = StoryModelMapper(sourceModelMapper)
    val savedStoryModelMapper = SavedStoryModelMapper(sourceModelMapper)

    val mapper = MapperFactory(
        PojoMappers(
            storyPojoMapper,
            sourcePojoMapper
        ),
        EntityMappers(
            storyEntityMapper,
            sourceEntityMapper,
            savedStoryEntityMapper
        ),
        DomainModelMappers(
            storyModelMapper,
            savedStoryModelMapper,
            sourceModelMapper
        )
    )
}