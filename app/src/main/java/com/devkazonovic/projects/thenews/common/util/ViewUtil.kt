package com.devkazonovic.projects.thenews.common.util

import android.view.View
import androidx.viewbinding.ViewBinding
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.show

object ViewUtil {

    fun hide(vararg views: View) {
        views.forEach {
            it.hide()
        }
    }

    fun show(vararg views: View) {
        views.forEach {
            it.show()
        }
    }

    fun hide(vararg views: ViewBinding) {
        views.forEach {
            it.hide()
        }
    }

    fun show(vararg views: ViewBinding) {
        views.forEach {
            it.show()
        }
    }


}