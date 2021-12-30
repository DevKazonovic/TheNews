package com.devkazonovic.projects.thenews.presentation.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val repository: MainRepository,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val _savedStories = MutableLiveData<Resource<List<Story>>>()

    fun load() {
        repository.getReadLaterStories()
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.uiScheduler())
            .subscribe { _savedStories.postValue(it) }
    }

    val savedStories: LiveData<Resource<List<Story>>> = _savedStories

}