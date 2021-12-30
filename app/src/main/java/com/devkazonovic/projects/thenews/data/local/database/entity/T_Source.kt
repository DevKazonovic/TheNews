package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.ColumnInfo
import java.io.Serializable
import java.util.*

data class T_Source(
    @ColumnInfo(name = "sourceId") val id: String,
    @ColumnInfo(name = "sourceName") val name: String,
    @ColumnInfo(name = "sourceUrl") val url: String
) : Serializable {
    companion object {
        const val NO_VALUE = "NON"
        val NO_SOURCE = T_Source(id = NO_VALUE, name = NO_VALUE, url = NO_VALUE)
        fun createSourceId(name: String?): String {
            return name?.let {
                StringBuilder().append(name.replace(" ", "_").trim()).append(
                    UUID.randomUUID().toString()
                ).toString()
            } ?: T_Story.NO_SOURCE_VALUE
        }
    }
}
