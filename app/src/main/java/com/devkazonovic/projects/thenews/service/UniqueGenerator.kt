package com.devkazonovic.projects.thenews.service

import com.devkazonovic.projects.thenews.data.local.database.entity.StoryEntity
import java.util.*
import javax.inject.Inject

class UniqueGenerator @Inject constructor() {

    //Todo : Implement Interface (For Testing)
    fun createSourceId(name: String?): String {
        return name?.let {
            StringBuilder().append(name.replace(" ", "_").trim()).append(
                UUID.randomUUID().toString()
            ).toString()
        } ?: StoryEntity.NO_SOURCE_VALUE
    }

}