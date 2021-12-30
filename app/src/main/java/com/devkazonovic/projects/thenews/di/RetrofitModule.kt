package com.devkazonovic.projects.thenews.di

import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.GoogleNewsClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val URL = "https://news.google.com/"

    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor {
        return StethoInterceptor()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(URL)
            client(okHttpClient)
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            addConverterFactory(SimpleXmlConverterFactory.create())
        }.build()
    }

    @Provides
    fun provideGoogleNewsClient(retrofit: Retrofit): GoogleNewsClient {
        return retrofit.create(GoogleNewsClient::class.java)
    }


}