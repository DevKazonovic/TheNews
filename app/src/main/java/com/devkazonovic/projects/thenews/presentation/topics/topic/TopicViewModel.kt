package com.devkazonovic.projects.thenews.presentation.topics.topic

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
class TopicViewModel @Inject constructor(
    private val repository: MainRepository,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val rxDisposable = CompositeDisposable()
    private val _topicId = MutableLiveData<String>()
    private val _stories = MutableLiveData<Resource<List<Story>>>()

    init {
        repository.getLanguageZoneObservable()
            .observeOn(schedulers.uiScheduler())
            .subscribe { languageZone ->
                loadData(reload = true, clearCache = true)
            }.addTo(rxDisposable)

    }

    fun loadData(reload: Boolean, clearCache: Boolean) {
        _stories.postValue(Resource.Loading())
        _topicId.value?.let {
            repository.getTopicStories(topicId = it, reload = reload, cleanCache = clearCache)
                .subscribeOn(schedulers.ioScheduler())
                .observeOn(schedulers.uiScheduler())
                .subscribe { resource ->
                    _stories.postValue(resource)
                }.addTo(rxDisposable)
        }
    }

    fun setTopicId(topicId: String) {
        _topicId.value = topicId
    }

    val stories: LiveData<Resource<List<Story>>> get() = _stories

    override fun onCleared() {
        super.onCleared()
        rxDisposable.clear()
    }

}