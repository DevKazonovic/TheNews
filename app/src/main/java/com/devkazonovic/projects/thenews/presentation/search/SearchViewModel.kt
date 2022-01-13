package com.devkazonovic.projects.thenews.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val rxDisposable = CompositeDisposable()
    private val _keyword = MutableLiveData<String>()
    private val _result = MutableLiveData<Resource<List<Story>>>()

    init {
        //load trending news
    }

    override fun onCleared() {
        super.onCleared()
        rxDisposable.clear()
    }

    fun search() {
        _result.value = Resource.Loading()
        _keyword.value?.let {
            if (it.isNotEmpty() && it.isNotBlank() && it.length >= 3) {
                mainRepository.searchByKeyword(keyword = it)
                    .subscribeOn(schedulers.ioScheduler())
                    .observeOn(schedulers.uiScheduler())
                    .subscribe { success -> _result.postValue(success) }
                    .addTo(rxDisposable)
            }
        }
    }

    fun setSearchKeyWord(keyword: String) {
        _keyword.value = keyword
    }

    val result: LiveData<Resource<List<Story>>> get() = _result
}