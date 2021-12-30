package com.devkazonovic.projects.thenews.androidTestHelp

import com.devkazonovic.projects.thenews.common.util.RxSchedulersFactory
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import io.reactivex.rxjava3.core.Single


class FakeArticleService : ArticleScrapper(RxSchedulersFactory()) {

    override fun getArticleImageUrl(url: String): Single<String> {
        return Single.just(FAKE_IMAGE_URL)
    }

    companion object {
        const val FAKE_IMAGE_URL = "https://domain.com/img.jpg"
    }

}