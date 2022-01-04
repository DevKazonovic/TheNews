package com.devkazonovic.projects.thenews.presentation.foryou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.data.local.sharedpref.LocalKeyValue
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.LanguageZone
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val localKeyValue: LocalKeyValue,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private var reload = true
    private var ceid = LanguageZone.DEFAULT.getCeId()

    private val _stories = MutableLiveData<Resource<List<Story>>>()

    fun loadData() {
        _stories.value = Resource.Loading()
        ceid = localKeyValue.getLanguageZone()
        mainRepository.getStories(ceid,reload)
            .subscribeOn(schedulers.ioScheduler())
            .observeOn(schedulers.uiScheduler())
            .subscribe { resource ->
                _stories.postValue(resource)
                reload = false
            }
    }

    val stories: LiveData<Resource<List<Story>>> = _stories
}