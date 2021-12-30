package com.devkazonovic.projects.thenews.data.local.sharedpref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val KEY_LANGUAGE_ZONE = "Current Selected Language-Zone"


class LocalKeyValue @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences =
        context.getSharedPreferences(context.packageName, MODE_PRIVATE)

    fun saveLanguageZone(ceid: String): Boolean {
        return with(preferences.edit()) {
            putString(KEY_LANGUAGE_ZONE, ceid)
        }.commit()
    }

    fun getLanguageZone(): String {
        return preferences.getString(KEY_LANGUAGE_ZONE, LanguageZone.DEFAULT.getCeId())
            ?: LanguageZone.DEFAULT.getCeId()
    }
}