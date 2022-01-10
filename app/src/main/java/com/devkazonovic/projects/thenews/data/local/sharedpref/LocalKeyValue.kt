package com.devkazonovic.projects.thenews.data.local.sharedpref

import android.content.SharedPreferences
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.local.database.MainDataBase
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

private const val KEY_LANGUAGE_ZONE = "Current Selected Language-Zone"


class LocalKeyValue @Inject constructor(
    private val preferences: SharedPreferences,
    mainDataBase: MainDataBase,
    rxSchedulers: RxSchedulers
) {

    private val storiesDao = mainDataBase.storiesDao()
    private val _behaviorSubject =
        BehaviorSubject.createDefault(getLanguageZone())
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (KEY_LANGUAGE_ZONE == key) {
                Completable.fromCallable { storiesDao.deleteCachedTopStories() }
                    .subscribeOn(rxSchedulers.ioScheduler())
                    .subscribe {
                        _behaviorSubject.onNext(
                            sharedPreferences.getString(key, LanguageZone.DEFAULT.getCeId())
                        )
                    }
            }
        }

    init {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun saveLanguageZone(ceid: String): Boolean {
        return with(preferences.edit()) {
            putString(KEY_LANGUAGE_ZONE, ceid)
        }.commit()
    }

    fun getLanguageZone(): String {
        return preferences.getString(KEY_LANGUAGE_ZONE, LanguageZone.DEFAULT.getCeId())
            ?: LanguageZone.DEFAULT.getCeId()
    }

    fun stopObservingSharedPreference() {
        behaviorSubject.onComplete()
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    val behaviorSubject: BehaviorSubject<String> get() = _behaviorSubject
}