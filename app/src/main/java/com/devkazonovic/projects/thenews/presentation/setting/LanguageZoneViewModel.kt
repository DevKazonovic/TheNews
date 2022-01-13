package com.devkazonovic.projects.thenews.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class LanguageZoneViewModel @Inject constructor(
    private val repository: MainRepository,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val _currentSelectedLanguage = MutableLiveData<LanguageZone>()
    private val _languageZoneList = MutableLiveData<List<LanguageZone>>()

    fun showList() {
        repository.getSupportedLanguagesZones()
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.uiScheduler())
            .subscribe { list ->
                val currentSelected =
                    list.findLast { it.getCeId() == repository.getCurrentLanguageZone() }
                _currentSelectedLanguage.value = currentSelected ?: LanguageZone.DEFAULT
                _languageZoneList.postValue(list.toMutableList().apply { remove(currentSelected) })

            }

    }

    fun onLanguageZoneSelected(languageZone: LanguageZone) {
        if (repository.saveLanguageZone(languageZone.getCeId())) {
            showList()
        }

    }

    fun searchForLanguageZone(input: String) {
        if (input.isBlank() || input.isEmpty()) {
            showList()
        } else {
            repository.getSupportedLanguagesZones()
                .flatMap { list ->
                    Observable.fromIterable(list)
                        .filter { it.name.contains(input, true) }
                        .toList()
                }.subscribeOn(rxSchedulers.ioScheduler())
                .observeOn(rxSchedulers.uiScheduler())
                .subscribe { result ->
                    _languageZoneList.postValue(result)
                }
        }
    }

    val languageZoneList: LiveData<List<LanguageZone>> get() = _languageZoneList
    val currentSelectedLanguage: LiveData<LanguageZone> get() = _currentSelectedLanguage
}