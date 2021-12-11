package com.devkazonovic.projects.thenews.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Story(
    val id: Long,
    val source: String,
    val imgUrl: String,
    val title: String,
    val publishDate: String
) : Serializable {

    companion object {
        val DIFF_UTIl = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

        }
    }
}
