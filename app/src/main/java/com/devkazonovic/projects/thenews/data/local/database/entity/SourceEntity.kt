package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.ColumnInfo
import java.io.Serializable

data class SourceEntity(
    @ColumnInfo(name = "source_id") val id: String,
    @ColumnInfo(name = "source_name") val name: String,
    @ColumnInfo(name = "source_url") val url: String
) : Serializable {
    companion object {
        private const val NO_SOURCE_VALUE = "NON"
        val NO_SOURCE_ENTITY =
            SourceEntity(id = NO_SOURCE_VALUE, name = NO_SOURCE_VALUE, url = NO_SOURCE_VALUE)

    }
}
