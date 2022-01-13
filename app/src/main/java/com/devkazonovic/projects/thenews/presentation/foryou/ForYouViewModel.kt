package com.devkazonovic.projects.thenews.presentation.foryou

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
class ForYouViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val rxDisposable = CompositeDisposable()
    private val _stories = MutableLiveData<Resource<List<Story>>>()

    init {
        mainRepository.getLanguageZoneObservable()
            .observeOn(schedulers.uiScheduler())
            .subscribe { languageZone ->
                loadData(reload = true, clearCache = true)
            }.addTo(rxDisposable)

    }

    fun loadData(reload: Boolean, clearCache: Boolean) {
        _stories.value = Resource.Loading()
        mainRepository.getTopStories(reload = reload, cleanCache = clearCache)
            .subscribeOn(schedulers.ioScheduler())
            .observeOn(schedulers.uiScheduler())
            .subscribe { resource ->
                _stories.postValue(resource)
            }.addTo(rxDisposable)
    }

    val stories: LiveData<Resource<List<Story>>> = _stories

    override fun onCleared() {
        super.onCleared()
        rxDisposable.clear()
    }
}