package com.devkazonovic.projects.thenews.domain.model

import com.devkazonovic.projects.thenews.data.local.database.entity.T_Story
import java.io.Serializable
import java.util.*

data class Source(
    val id: String,
    val name: String,
    val url: String = ""
) : Serializable {
    companion object {
        const val NON_VALUE = "NON"
        val NO_SOURCE = Source(id = NON_VALUE, name = NON_VALUE)
        fun createSourceId(name: String?): String {
            return name?.let {
                StringBuilder().append(name.replace(" ", "_").trim()).append(
                    UUID.randomUUID().toString()
                ).toString()
            } ?: T_Story.NO_SOURCE_VALUE
        }
    }
}
