package com.devkazonovic.projects.thenews.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val localKeyValue: LocalKeyValue,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()
    private val _result = MutableLiveData<Resource<List<Story>>>()
    private val ceid = localKeyValue.getLanguageZone()

    fun search() {
        _result.value = Resource.Loading()
        _keyword.value?.let {
            if (it.isNotEmpty() && it.isNotBlank() && it.length >= 3) {
                mainRepository.searchByKeyword(it, ceid)
                    .subscribeOn(schedulers.ioScheduler())
                    .observeOn(schedulers.uiScheduler())
                    .subscribe { success -> _result.postValue(success) }
            }
        }
    }

    fun setSearchKeyWord(keyword: String) {
        _keyword.value = keyword
    }

    val result: LiveData<Resource<List<Story>>> get() = _result
}