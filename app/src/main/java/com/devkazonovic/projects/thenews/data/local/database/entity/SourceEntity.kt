package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.ColumnInfo
import java.io.Serializable

data class SourceEntity(
    @ColumnInfo(name = "sourceId") val id: String,
    @ColumnInfo(name = "sourceName") val name: String,
    @ColumnInfo(name = "sourceUrl") val url: String
) : Serializable {
    companion object {
        const val NO_VALUE = "NON"
        val NO_SOURCE = SourceEntity(id = NO_VALUE, name = NO_VALUE, url = NO_VALUE)

    }
}
