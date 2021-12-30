package com.devkazonovic.projects.thenews.presentation.headlines.headline

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
class HeadlineViewModel @Inject constructor(
    private val repository: MainRepository,
    private val localKeyValue: LocalKeyValue,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val _stories = MutableLiveData<Resource<List<Story>>>()
    private var _languageZoneId = localKeyValue.getLanguageZone()
    private val _topicId = MutableLiveData<String>()

    fun load() {
        _stories.postValue(Resource.Loading())
        _languageZoneId = localKeyValue.getLanguageZone()
        _topicId.value?.let {
            repository.getTopicStories(topicId = it, languageZoneId = _languageZoneId)
                .subscribeOn(schedulers.ioScheduler())
                .observeOn(schedulers.uiScheduler())
                .subscribe { resource ->
                    _stories.postValue(resource)
                }
        }
    }

    fun setTopicId(topicId: String) {
        _topicId.value = topicId
    }

    val stories: LiveData<Resource<List<Story>>> get() = _stories

}