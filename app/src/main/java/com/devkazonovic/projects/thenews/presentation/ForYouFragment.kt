package com.devkazonovic.projects.thenews.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.databinding.FragmentForyouBinding
import com.devkazonovic.projects.thenews.dummy.Dummy
import com.devkazonovic.projects.thenews.model.Story


class ForYouFragment : Fragment() {

    private var _binding: FragmentForyouBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForyouBinding.inflate(inflater, container, false)
        val root = binding.root
        navController = findNavController()
        binding.let {
            toolbar = it.topAppBar
            drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
            viewPager = it.viewPageTopStories
            recyclerView = it.recyclerViewState
        }
        toolbar.setMainPageToolbar(navController, drawerLayout)
        val stories = Dummy.stories
        val pagerAdapter = TopStoriesViewPager(requireActivity(), stories)
        val stateList = StoriesStateListAdapter.initStateList(stories.size)
        val stateAdapter = StoriesStateListAdapter(stateList)
        viewPager.setPageTransformer(MarginPageTransformer(28))
        viewPager.adapter = pagerAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = stateAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                stateAdapter.updateList(position)
            }
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = ForYouFragment()
    }
}