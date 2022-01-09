package com.devkazonovic.projects.thenews.presentation.common.storymenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devkazonovic.projects.thenews.common.model.Event
import com.devkazonovic.projects.thenews.common.util.RxSchedulers
import com.devkazonovic.projects.thenews.domain.MainRepository
import com.devkazonovic.projects.thenews.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class StoryMenuViewModel @Inject constructor(
    private val repository: MainRepository,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val rxDisposable = CompositeDisposable()

    private var _story: Story? = null

    private val _actionComplete = MutableLiveData<Event<Boolean>>()

    fun setStory(story: Story?) {
        _story = story
    }

    private fun isStorySaved() {
        val savedStory = repository.isStorySavedToReadLater(_story!!.url)
            .subscribeOn(rxSchedulers.ioScheduler())
            .blockingGet()
        _story = _story?.copy(isSaved = savedStory != null)
    }

    fun isCurrentStorySavedToReadLater(): Boolean {
        isStorySaved()
        return _story!!.isSaved
    }

    fun saveStoryToReadLater() {
        _story?.let {
            repository.saveStoryToReadLater(it)
                .subscribeOn(rxSchedulers.ioScheduler())
                .observeOn(rxSchedulers.uiScheduler())
                .subscribe({
                    _actionComplete.value = Event(true)
                }, { error ->
                    _actionComplete.value = Event(false)
                }).addTo(rxDisposable)
        }
    }

    fun removeStoryFromReadLater() {
        _story?.let {
            repository.deleteStoryToReadLater(it)
                .subscribeOn(rxSchedulers.ioScheduler())
                .observeOn(rxSchedulers.uiScheduler())
                .subscribe({
                    _actionComplete.value = Event(true)
                }, { error ->
                    _actionComplete.value = Event(false)
                }).addTo(rxDisposable)
        }

    }

    val isActionCompleted: LiveData<Event<Boolean>> = _actionComplete
    val story get() = _story

}