package com.devkazonovic.projects.thenews.common.util

import android.content.Context
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.domain.model.Ago

object DateTimeUtil {

    fun showTimePassed(context: Context, pair: Pair<Int, Ago>): String {
        val resource = context.resources
        val howMuchAgo = pair.first
        return when (pair.second) {
            Ago.YEAR -> resource.getQuantityString(R.plurals.year, howMuchAgo, howMuchAgo)
            Ago.MONTH -> resource.getQuantityString(R.plurals.month, howMuchAgo, howMuchAgo)
            Ago.DAY -> resource.getQuantityString(R.plurals.day, howMuchAgo, howMuchAgo)
            Ago.HOUR -> resource.getQuantityString(R.plurals.hour, howMuchAgo, howMuchAgo)
            Ago.MINUTE -> resource.getQuantityString(R.plurals.minute, howMuchAgo, howMuchAgo)
            Ago.NON -> "Now"
        } + " " + context.getString(R.string.ego)
    }

}