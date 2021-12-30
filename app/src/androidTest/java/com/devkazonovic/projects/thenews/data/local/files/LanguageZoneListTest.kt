package com.devkazonovic.projects.thenews.data.local.files

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LanguageZoneListTest {

    private lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getListOfSupportedLanguagesAndZonesFromGoogleNews() {
        val actual =
            LanguageZoneList(instrumentationContext).getList()
        assertThat(actual).apply {
            isNotEmpty()
            contains(LanguageZone.DEFAULT)
        }
    }


}