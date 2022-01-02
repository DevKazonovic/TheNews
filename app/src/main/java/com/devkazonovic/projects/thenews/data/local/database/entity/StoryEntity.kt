package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "story")
data class StoryEntity(
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
        val EMPTY = StoryEntity(url = "", sourceEntity = NO_SOURCE, title = "", publishDate = "")

    }
}

