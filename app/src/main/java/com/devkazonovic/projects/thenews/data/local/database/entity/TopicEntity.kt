package com.devkazonovic.projects.thenews.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "topics")
data class TopicEntity(
    @ColumnInfo(name = "topic_google_news_id")
    val topicGoogleId: String,
    @ColumnInfo(name = "topic_string_xml_id")
    val topicStringId: Int
) : Serializable {

    @ColumnInfo(name = "topic_id")
    @PrimaryKey(autoGenerate = true)
    var topicId: Long = 0
}
