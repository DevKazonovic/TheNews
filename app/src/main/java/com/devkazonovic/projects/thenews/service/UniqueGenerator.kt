package com.devkazonovic.projects.thenews.service

import java.util.*
import javax.inject.Inject

class UniqueGenerator @Inject constructor() {

    fun createSourceId(name: String?): String {
        return name?.let {
            StringBuilder().append(name.replace(" ", "_").trim()).append(
                UUID.randomUUID().toString()
            ).toString()
        } ?: "NON"
    }

}