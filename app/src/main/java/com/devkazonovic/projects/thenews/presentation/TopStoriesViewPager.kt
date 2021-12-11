package com.devkazonovic.projects.thenews.presentation

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.model.Story

class TopStoriesViewPager(activity: FragmentActivity, private val stories : List<Story>)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = stories.size

    override fun createFragment(position: Int): Fragment {
        return TopStoryFragment.newInstance(stories[position],position)
    }
}