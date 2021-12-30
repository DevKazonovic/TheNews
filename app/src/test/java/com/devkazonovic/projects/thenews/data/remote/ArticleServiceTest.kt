package com.devkazonovic.projects.thenews.data.remote

import com.devkazonovic.projects.thenews.common.util.RxSchedulersFactory
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ArticleServiceTest {

    private lateinit var articleService: ArticleScrapper

    @Before
    fun setUp() {
        articleService = ArticleScrapper(RxSchedulersFactory())
    }

    @Test
    fun testGettingArticleImageFromMeta_UsingOpenGraphMeta() {
        val url =
            "https://2m.ma/fr/news/meteomaroc-le-mercure-toujours-en-baisse-ce-lundi-20-decembre-20211220/"
        val actual = articleService.getArticleImageUrl(url)
        val expected =
            "http://2m.ma/site_media/uploads/mediasfiles/2020/9/30/1601488881/1601488881Ciel_nuageux_M%C3%A9t%C3%A9o_2KXwV0w.jpg"

        assertThat(actual).isEqualTo(expected)
    }


}