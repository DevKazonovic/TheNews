package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.domain.model.Story

class CarouselAdapter(
    fragment: Fragment,
    private val stories: List<Story> = listOf()
) : FragmentStateAdapter(fragment) {


    private val pagesIds = stories.map { it.hashCode().toLong() }

    override fun getItemCount(): Int = stories.size

    override fun createFragment(position: Int): Fragment {
        return TopStoryItemFragment.newInstance(stories[position], position)
    }

    override fun getItemId(position: Int): Long {
        return stories[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return pagesIds.contains(itemId)
    }


}