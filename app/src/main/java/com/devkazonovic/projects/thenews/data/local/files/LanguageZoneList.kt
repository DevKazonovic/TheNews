package com.devkazonovic.projects.thenews.data.local.files

import android.content.Context
import com.devkazonovic.projects.thenews.common.util.IOUtil
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import dagger.hilt.android.qualifiers.ApplicationContext
import org.jsoup.Jsoup
import javax.inject.Inject

class LanguageZoneList @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val SELECTOR_ITEM = "li.LxUgAe"
        private const val SELECTOR_LABEL = "label"
        private const val SELECTOR_VALUE = "input[type=\"radio\"]"
        private const val ATTR_TYPES_INPUT = "value"
        private const val DELIMITER = ":"
    }


    fun getList(): List<LanguageZone> {
        val assetManager = context.assets
        val doc = Jsoup.parse(
            IOUtil.getAssetFileData(assetManager, "language_zone_list.html")
        )

        return doc.select(SELECTOR_ITEM).map { item ->
            val ceid = item.selectFirst(SELECTOR_VALUE)
                ?.attr(ATTR_TYPES_INPUT)?.split(DELIMITER)
            val label = item.selectFirst(SELECTOR_LABEL)
                ?.text()

            if (ceid != null && label != null) LanguageZone(ceid[0], ceid[1], label)
            else LanguageZone.DEFAULT
        }.sortedBy { it.name }
    }
}