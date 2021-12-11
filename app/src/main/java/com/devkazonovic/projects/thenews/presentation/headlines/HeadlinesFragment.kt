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
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.databinding.FragmentHeadlinesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HeadlinesFragment : Fragment() {


    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var vpHeadlines : ViewPager2
    private lateinit var tabLayoutCategories : TabLayout

    private lateinit var adapterVPCategories: CategoriesViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        val root = binding.root
        initViews()
        navController = findNavController()
        toolbar.setMainPageToolbar(navController, drawerLayout)

        adapterVPCategories = CategoriesViewPagerAdapter(
            requireActivity(),
            listOf(HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance(), HeadlineFragment.newInstance())
        )
        vpHeadlines.adapter = adapterVPCategories

        TabLayoutMediator(tabLayoutCategories, vpHeadlines){tab, position ->
            tab.text = "TAB ${(position + 1)}" }.attach()

        return root
    }

    private fun initViews(){
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        binding.let {
            toolbar = it.topAppBar
            vpHeadlines = it.vpHeadlines
            tabLayoutCategories = it.tabs
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HeadlinesFragment()
    }
}