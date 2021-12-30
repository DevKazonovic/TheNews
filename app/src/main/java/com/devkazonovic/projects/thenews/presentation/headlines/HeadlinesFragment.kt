package com.devkazonovic.projects.thenews.presentation.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.data.local.Topics
import com.devkazonovic.projects.thenews.databinding.FragmentHeadlinesBinding
import com.devkazonovic.projects.thenews.domain.model.Topic
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlinesFragment : Fragment() {

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var vpHeadlines: ViewPager2
    private lateinit var tabLayoutCategories: TabLayout
    private lateinit var adapterVPCategories: FragmentStateAdapter
    private lateinit var tabs: List<Topic>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        initViews()
        navController = findNavController()
        toolbar.setMainPageToolbar(navController, drawerLayout)
        tabs = Topics.getTopics(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterVPCategories = TopicsViewPagerAdapter(childFragmentManager, lifecycle, tabs)
        vpHeadlines.adapter = adapterVPCategories
        tabLayoutCategories.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vpHeadlines.setCurrentItem(tab.position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                vpHeadlines.setCurrentItem(tab.position, false)
            }
        })
        TabLayoutMediator(tabLayoutCategories, vpHeadlines) { tab, position ->
            tab.text = tabs[position].title
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        binding.let {
            toolbar = it.topAppBar
            vpHeadlines = it.vpHeadlines
            tabLayoutCategories = it.tabs
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = HeadlinesFragment()
    }
}