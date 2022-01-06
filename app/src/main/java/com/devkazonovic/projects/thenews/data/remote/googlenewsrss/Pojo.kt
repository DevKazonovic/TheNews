package com.devkazonovic.projects.thenews.data.remote.googlenewsrss

import org.simpleframework.xml.*
import java.io.Serializable

@Root(name = "rss", strict = false)
class RSS @JvmOverloads constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: Channel? = null
) : Serializable

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    val items: List<Item>? = null,
    @field:Element(name = "lastBuildDate")
    @param:Element(name = "lastBuildDate")
    val lastBuildDate: String? = null
) : Serializable

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,
    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String? = null,
    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val pubDate: String? = null,
    @field:Element(name = "source")
    @param:Element(name = "source")
    val itemSource: ItemSource? = null
) : Serializable

@Root(name = "source", strict = false)
data class ItemSource @JvmOverloads constructor(
    @field:Attribute(name = "url", required = false)
    var url: String? = null,
    @field:Text
    var text: String? = null
) : Serializable