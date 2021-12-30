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

    private val _languageZoneList = MutableLiveData<List<LanguageZone>>()

    fun showList() {
        _languageZoneList.postValue(listProvider.getList())
    }

    fun onLanguageZoneSelected(cied: String) {
        sharedPreferences.saveLanguageZone(cied)
    }

    fun getCurrentLanguageZone(): String {
        return sharedPreferences.getLanguageZone()
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
}