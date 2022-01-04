package com.devkazonovic.projects.thenews.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.data.local.files.LanguageZoneList
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageZoneViewModel @Inject constructor(
    private val listProvider: LanguageZoneList,
    private val sharedPreferences: LocalKeyValue
) : ViewModel() {

    private val _currentSelectedLanguage = MutableLiveData<LanguageZone>()
    private val _languageZoneList = MutableLiveData<List<LanguageZone>>()

    fun showList() {
        val list = listProvider.getList()
        val currentSelected = list.findLast { it.getCeId() == sharedPreferences.getLanguageZone() }
        _currentSelectedLanguage.value = currentSelected ?: LanguageZone.DEFAULT
        _languageZoneList.postValue(list.toMutableList().apply { remove(currentSelected) })
    }

    fun onLanguageZoneSelected(languageZone: LanguageZone) {
        if (sharedPreferences.saveLanguageZone(languageZone.getCeId())) {
            showList()
        }

    }

    fun searchForLanguageZone(input: String) {
        if (input.isBlank() || input.isEmpty()) {
            showList()
        } else {
            val filteredList =
                listProvider.getList().filter { it.name.contains(input, true) }
            _languageZoneList.postValue(filteredList)
        }
    }

    val languageZoneList: LiveData<List<LanguageZone>> get() = _languageZoneList
    val currentSelectedLanguage: LiveData<LanguageZone> get() = _currentSelectedLanguage
}