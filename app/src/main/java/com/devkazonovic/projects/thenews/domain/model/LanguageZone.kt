package com.devkazonovic.projects.thenews.domain.model

import androidx.recyclerview.widget.DiffUtil

data class LanguageZone(
    val zone: String,
    val language: String,
    val name: String
) {
    fun getCeId(): String {
        return "$zone:$language"
    }


    override fun toString(): String {
        return getCeId()
    }

    companion object {
        val DEFAULT = LanguageZone("US", "en", "English (United States)")
        val DIFF_UTIL =
            object : DiffUtil.ItemCallback<LanguageZone>() {
                override fun areItemsTheSame(
                    oldItem: LanguageZone,
                    newItem: LanguageZone
                ): Boolean {
                    return oldItem.getCeId() == newItem.getCeId()
                }

                override fun areContentsTheSame(
                    oldItem: LanguageZone,
                    newItem: LanguageZone
                ): Boolean {
                    return oldItem.name == newItem.name
                }
            }

        fun getLanguageZone(ceid: String): Pair<String, String> {
            val values = ceid.split(":")
            return Pair(values[0], values[1])
        }
    }
}
