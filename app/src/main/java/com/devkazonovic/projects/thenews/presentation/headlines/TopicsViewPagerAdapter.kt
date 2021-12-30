package com.devkazonovic.projects.thenews.presentation.headlines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.domain.model.Topic
import com.devkazonovic.projects.thenews.presentation.headlines.headline.HeadlineFragment

class TopicsViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val tabs: List<Topic>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return HeadlineFragment.newInstance(tabs[position].id)
    }


}