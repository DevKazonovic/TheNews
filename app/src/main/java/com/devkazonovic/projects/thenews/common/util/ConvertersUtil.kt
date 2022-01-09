package com.devkazonovic.projects.thenews.common.util

object ConvertersUtil {

    fun fromBooleanToInt(bol: Boolean): Int {
        return if (bol) 1 else 0
    }

}