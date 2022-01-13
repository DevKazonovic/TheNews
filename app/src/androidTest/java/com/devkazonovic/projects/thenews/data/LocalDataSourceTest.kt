package com.devkazonovic.projects.thenews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devkazonovic.projects.thenews.AndroidTestFactory
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import com.devkazonovic.projects.thenews.fake.FakeSharedPreferences
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var localDataSource: LocalDataSource

    @Test
    fun test_whenChangingLanguageZone_returnTrue() {
        val zoneId = "MA:fr"
        assertThat(localDataSource.saveLanguageZone(zoneId)).isTrue()
    }

    @Test
    fun test_whenGetLanguageZone_returnCurrentSavedValue() {
        val expected = "MA:fr"
        localDataSource.saveLanguageZone(expected)

        assertThat(localDataSource.getLanguageZone()).isEqualTo(expected)
    }

    @Test
    fun test_whenChangingLanguageZone_emitTheNewValueToAllObservers() {
        val expected1 = "MA:fr"
        localDataSource.saveLanguageZone(expected1)

        localDataSource.getLanguageZoneObservable()
            .test()
            .assertValues(expected1)

        val expected2 = "US:en"
        localDataSource.saveLanguageZone(expected2)

        localDataSource.getLanguageZoneObservable()
            .test()
            .assertValues(expected2)

    }

    @Before
    fun setUp() {
        localDataSource = LocalDataSource(
            AndroidTestFactory.mainDataBase(
                ApplicationProvider.getApplicationContext()
            ),
            LocalKeyValue(FakeSharedPreferences())
        )
    }

}