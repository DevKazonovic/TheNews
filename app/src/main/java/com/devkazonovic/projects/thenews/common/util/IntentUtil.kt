package com.devkazonovic.projects.thenews.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.devkazonovic.projects.thenews.domain.model.Story


object IntentUtil {

    fun sharedUrl(context: Context, story: Story) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, story.url)
            putExtra(Intent.EXTRA_TITLE, story.title)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, story.title)
        context.startActivity(shareIntent)
    }

    fun openUrl(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().apply {
        }.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

}