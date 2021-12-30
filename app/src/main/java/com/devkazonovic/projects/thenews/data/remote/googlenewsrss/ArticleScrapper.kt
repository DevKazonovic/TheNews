package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup
import javax.inject.Inject

private const val OPEN_GRAPH_META_IMG = "og:image"
private const val OPEN_GRAPH_META_DESC = "og:description"
private const val TAG_HEAD = "head"
private const val ATTRIBUTE_CONTENT = "content"

open class ArticleScrapper @Inject constructor(
    private val schedulers: RxSchedulers
) {


    open fun getArticleImageUrl(url: String): Single<String> {
        return Single.fromCallable {
            val doc = Jsoup.connect(url)
                .followRedirects(true).get()
            doc.select(TAG_HEAD)
                .select("meta[property=$OPEN_GRAPH_META_IMG]")
                .attr(ATTRIBUTE_CONTENT)
        }.subscribeOn(schedulers.ioScheduler())
            .observeOn(schedulers.uiScheduler())
    }
}