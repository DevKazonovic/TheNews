package com.devkazonovic.projects.thenews.common.util

import android.content.Context
import android.util.TypedValue

object MaterialUtil {

    fun getStyleId(context: Context, id: Int): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(id, typedValue, true)
        return typedValue.data
    }
}