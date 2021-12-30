package com.devkazonovic.projects.thenews.common.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface RxSchedulers {
    fun ioScheduler(): Scheduler
    fun computationScheduler(): Scheduler
    fun uiScheduler(): Scheduler

}

class RxSchedulersFactory @Inject constructor() : RxSchedulers {
    override fun ioScheduler(): Scheduler = Schedulers.io()
    override fun computationScheduler(): Scheduler = Schedulers.computation()
    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}