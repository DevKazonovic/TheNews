package com.devkazonovic.projects.thenews.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.model.Event
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryMenuViewModel @Inject constructor(
    private val repository: MainRepository,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {
    private var _story: Story? = null

    private val _actionComplete = MutableLiveData<Event<Boolean>>()


    fun setStory(story: Story?) {
        _story = story
    }

    fun saveStoryToReadLater() {
        repository.saveStoryToReadLater(_story!!)
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.uiScheduler())
            .subscribe({
                _actionComplete.value = Event(true)
            }, {
                _actionComplete.value = Event(false)
            })
    }

    fun removeStoryFromReadLater() {
        repository.deleteStoryToReadLater(_story!!)
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.uiScheduler())
            .subscribe({
                _actionComplete.value = Event(true)
            }, {
                _actionComplete.value = Event(false)
            })
    }

    val isActionCompleted: LiveData<Event<Boolean>> = _actionComplete

}