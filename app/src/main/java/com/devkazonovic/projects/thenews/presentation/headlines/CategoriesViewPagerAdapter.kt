package com.devkazonovic.projects.thenews.presentation.headlines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoriesViewPagerAdapter(
    activity: FragmentActivity,
    private val fragments : List<HeadlineFragment>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}