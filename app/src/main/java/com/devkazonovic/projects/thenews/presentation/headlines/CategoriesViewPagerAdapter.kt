package com.devkazonovic.projects.thenews.presentation.headlines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devkazonovic.projects.thenews.domain.model.Topic
import com.devkazonovic.projects.thenews.presentation.headlines.headline.HeadlineFragment

class CategoriesViewPagerAdapter(
    activity: FragmentActivity,
    private val tabs: List<Topic>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return HeadlineFragment.newInstance(tabs[position].id)
    }
}