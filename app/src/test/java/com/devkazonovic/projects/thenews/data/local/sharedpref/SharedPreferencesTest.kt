package com.devkazonovic.projects.thenews.data.local.sharedpref

import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class SharedPreferencesTest {

    private lateinit var localKeyValue : LocalKeyValue

    @Before
    fun setUp() {
        localKeyValue = LocalKeyValue(FakeSharedPreferences())
    }

    @Test
    fun test_whenObservingSharedPreferenceForFirstTime_thenGetCurrentSavedLanguageZoneValue(){
        localKeyValue.saveLanguageZone(LanguageZone.DEFAULT.getCeId())
        val testObserver = localKeyValue.behaviorSubject.test()
        testObserver.assertValue(LanguageZone.DEFAULT.getCeId())
        testObserver.assertNotComplete()

    }

    @Test
    fun test_whenLanguageZoneValueChanges_thenGetNewValue(){
        //Given
        localKeyValue.saveLanguageZone(LanguageZone.DEFAULT.getCeId())
        val testObserver = localKeyValue.behaviorSubject.test()
        testObserver.assertValue(LanguageZone.DEFAULT.getCeId())

        //When
        val ceid = "MA:fr"
        localKeyValue.saveLanguageZone(ceid)

        //Then
        testObserver.assertValueSequence(listOf(LanguageZone.DEFAULT.getCeId(),ceid))
        testObserver.assertNotComplete()
    }

    @Test
    fun test_whenLanguageZoneValueChanges_thenGetNewValue_andComplete(){
        //Given
        localKeyValue.saveLanguageZone(LanguageZone.DEFAULT.getCeId())
        val testObserver = localKeyValue.behaviorSubject.test()
        testObserver.assertValue(LanguageZone.DEFAULT.getCeId())

        //When
        val ceid = "MA:fr"
        localKeyValue.saveLanguageZone(ceid)
        localKeyValue. stopObservingSharedPreference()
        //Then
        testObserver.assertResult(LanguageZone.DEFAULT.getCeId(),ceid)
    }
}