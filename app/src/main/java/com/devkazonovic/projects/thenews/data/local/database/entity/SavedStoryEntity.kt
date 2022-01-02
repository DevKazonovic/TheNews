package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "readlater")
data class SavedStoryEntity(
    val url: String,
    val title: String,
    @Embedded val sourceEntity: SourceEntity,
    val topicId: String? = null,
    val imgUrl: String = "",
    val publishDate: String = "",
    val description: String = "",
    val author: String = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val NO_SOURCE_VALUE = "NON"
        val NO_SOURCE =
            SourceEntity(id = NO_SOURCE_VALUE, name = NO_SOURCE_VALUE, url = NO_SOURCE_VALUE)
        val EMPTY =
            SavedStoryEntity(url = "", sourceEntity = NO_SOURCE, title = "", publishDate = "")

        fun createSourceId(name: String?): String {
            return name?.let {
                StringBuilder().append(name.replace(" ", "_").trim()).append(
                    UUID.randomUUID().toString()
                ).toString()
            } ?: NO_SOURCE_VALUE
        }
    }
}
