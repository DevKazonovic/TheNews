package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ArticleScrapperTest {

    private lateinit var articleService: ArticleScrapper

    @Before
    fun setUp() {
        articleService = ArticleScrapper(AndroidTestFactory.TestRxSchedulersFactory())
    }

    @Test
    fun testGettingArticleImageFromMeta_UsingOpenGraphMeta() {
        val url =
            "https://2m.ma/fr/news/meteomaroc-le-mercure-toujours-en-baisse-ce-lundi-20-decembre-20211220/"
        val expected =
            "http://2m.ma/site_media/uploads/mediasfiles/2020/9/30/1601488881/1601488881Ciel_nuageux_M%C3%A9t%C3%A9o_2KXwV0w.jpg"

        articleService.getArticleImageUrl(url)
            .test()
            .assertValue {
                assertThat(it).isEqualTo(expected)
                true
            }


    }

}