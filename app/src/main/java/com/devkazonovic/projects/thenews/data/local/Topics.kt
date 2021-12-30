package com.devkazonovic.projects.thenews.data.local

import android.content.Context
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.domain.model.Topic

object Topics {
    fun getTopics(context: Context): List<Topic> {
        return listOf(
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx1YlY4U0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_world)
            ),
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx6TVdZU0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_business)
            ),
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNRGRqTVhZU0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_tech)
            ),
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNREpxYW5RU0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_entertainment)
            ),
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNRFp1ZEdvU0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_sport)
            ),
            Topic(
                "CAAqJggKIiBDQkFTRWdvSUwyMHZNRFp0Y1RjU0FtVnVHZ0pWVXlnQVAB",
                context.getString(R.string.topic_science)
            ),
            Topic(
                "CAAqIQgKIhtDQkFTRGdvSUwyMHZNR3QwTlRFU0FtVnVLQUFQAQ",
                context.getString(R.string.topic_health)
            ),
        )
    }

}