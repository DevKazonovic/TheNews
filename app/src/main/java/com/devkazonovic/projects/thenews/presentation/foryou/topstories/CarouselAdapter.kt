package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.domain.model.Story

class CarouselAdapter(
    lifecycle: Lifecycle,
    manager: FragmentManager,
    private val stories: List<Story> = listOf()
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int = stories.size

    override fun createFragment(position: Int): Fragment {
        return TopStoryFragment.newInstance(stories[position], position)
    }

}