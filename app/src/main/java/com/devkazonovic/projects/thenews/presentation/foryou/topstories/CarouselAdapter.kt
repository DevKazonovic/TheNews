package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.domain.model.Story

class CarouselAdapter(
    activity: FragmentActivity,
    private val stories: List<Story> = listOf()
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = stories.size

    override fun createFragment(position: Int): Fragment {
        return TopStoryFragment.newInstance(stories[position], position)
    }

    fun setList(stories: List<Story>){
        stories.toMutableList().apply {
            clear()
            addAll(stories)
        }
    }

}