package com.devkazonovic.projects.thenews.data

import com.devkazonovic.projects.thenews.MyFactory
import com.devkazonovic.projects.thenews.MyFactory.URL_GOOGLE_NEWS_FEED
import com.devkazonovic.projects.thenews.data.remote.GoogleNewsClient

class RemoteDataSource {

    private val googleNewsClient: GoogleNewsClient =
        MyFactory.api(URL_GOOGLE_NEWS_FEED).create(GoogleNewsClient::class.java)


}