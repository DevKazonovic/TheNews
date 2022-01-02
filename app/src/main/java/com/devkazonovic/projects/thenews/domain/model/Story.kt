package com.devkazonovic.projects.thenews.domain.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Story(
    val url: String,
    val title: String,
    val source: Source,
    val publishDate: String = "",
    val publishDateFormat: Pair<Int, Ago> = Pair(0, Ago.NON),
    val imgUrl: String = "",
    val description: String = "",
    val author: String = "",
    val isSaved: Boolean = false
) : Serializable {

    companion object {

        val EMPTY = Story(url = "", source = Source.NO_SOURCE, title = "", publishDate = "")

        val DIFF_UTIl = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

        }

    }
}
