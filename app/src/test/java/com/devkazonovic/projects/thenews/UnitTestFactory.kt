package com.devkazonovic.projects.thenews

import com.devkazonovic.projects.thenews.domain.mapper.*
import com.devkazonovic.projects.thenews.service.DateTimeFormatter
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import timber.log.Timber

object UnitTestFactory {

    const val URL_GOOGLE_NEWS_FEED = "https://news.google.com/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor { println(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(HttpLoggingInterceptor { Timber.d(it) }.setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

    fun api(baseUrl: String, ioScheduler: Scheduler = Schedulers.io()) = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(okHttpClient)
        addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(ioScheduler))
        addConverterFactory(SimpleXmlConverterFactory.create())
    }.build()


    val mapper =  MapperFactory(
        storyXmlDataModel = StoryXmlDataModel(DateTimeFormatter(clock = Clock.systemUTC())),
        sourceXmlDataModel = SourceXmlDataModel(),
        storyEntityDataModel = StoryEntityDataModel(),
        savedStoryEntityDataModel = SavedStoryEntityDataModel(),
        sourceEntityDataModel = SourceEntityDataModel(),
        storyDataModelEntity = StoryDataModelEntity(),
        savedStoryDataModelEntity = SavedStoryDataModelEntity(),
        sourceDataModelEntity = SourceDataModelEntity()
    )


}