package com.devkazonovic.projects.thenews.common.extensions

import android.view.View

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.disable() {
    this.isEnabled = false
    this.isClickable = false
    this.isFocusable = false
}

fun Array<out View>.disable() {
    this.forEach { it.disable() }
}

fun View.enable() {
    this.isEnabled = true
    this.isClickable = true
    this.isFocusable = true
}

fun Array<out View>.enable() {
    this.forEach { it.enable() }
}