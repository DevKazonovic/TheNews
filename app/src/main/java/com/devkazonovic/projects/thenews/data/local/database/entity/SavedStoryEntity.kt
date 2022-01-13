package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "savedstories")
data class SavedStoryEntity(
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @Embedded val sourceEntity: SourceEntity,
    @ColumnInfo(name = "img_url") val imgUrl: String = "",
    @ColumnInfo(name = "date_publish") val publishDate: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "author") val author: String = ""
) : Serializable {

    companion object {
        val EMPTY =
            SavedStoryEntity(
                url = "",
                sourceEntity = SourceEntity.NO_SOURCE_ENTITY,
                title = "",
                publishDate = ""
            )

    }
}
