package com.devkazonovic.projects.thenews.presentation.following

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
class FollowingViewModel @Inject constructor(
    private val repository: MainRepository,
    private val rxSchedulers: RxSchedulers
) : ViewModel() {

    private val rxDisposable = CompositeDisposable()
    private val _savedStories = MutableLiveData<Resource<List<Story>>>()

    init {
        loadData()
    }

    override fun onCleared() {
        super.onCleared()
        rxDisposable.clear()
    }

    fun loadData() {
        repository.getReadLaterStories()
            .subscribeOn(rxSchedulers.ioScheduler())
            .observeOn(rxSchedulers.uiScheduler())
            .subscribe { _savedStories.postValue(it) }
            .addTo(rxDisposable)
    }

    val savedStories: LiveData<Resource<List<Story>>> = _savedStories

}