package com.devkazonovic.projects.thenews.domain.model

import java.io.Serializable

data class Source(
    val id: String,
    val name: String,
    val url: String
) : Serializable {
    companion object {
        const val EMPTY_VALUE = ""
        const val NON_VALUE = "NON"
        val NO_SOURCE = Source(id = NON_VALUE, name = NON_VALUE, url = EMPTY_VALUE)
    }
}
