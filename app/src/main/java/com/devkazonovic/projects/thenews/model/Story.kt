package com.devkazonovic.projects.thenews.model

import java.io.Serializable

data class Story(
    val source : String,
    val imgUrl : String,
    val title : String,
    val publishDate : String
) : Serializable
