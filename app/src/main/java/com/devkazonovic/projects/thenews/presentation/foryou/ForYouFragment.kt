package com.devkazonovic.projects.thenews.presentation.foryou

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.databinding.FragmentForyouBinding
import com.devkazonovic.projects.thenews.dummy.Dummy
import com.devkazonovic.projects.thenews.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.TopStoriesStateAdapter
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.TopStoriesViewPagerAdapter


class ForYouFragment : Fragment() {

    private var _binding: FragmentForyouBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPagerTopStories: ViewPager2
    private lateinit var rvTopStoriesCarouselState: RecyclerView
    private lateinit var rvStories: RecyclerView

    private lateinit var storiesListAdapter: StoriesListAdapter
    private lateinit var topStoriesCarouselItemsAdapter: TopStoriesViewPagerAdapter
    private lateinit var topStoriesCarouselStateAdapter: TopStoriesStateAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForyouBinding.inflate(inflater, container, false)
        val root = binding.root
        getViews()
        navController = findNavController()
        toolbar.setMainPageToolbar(navController, drawerLayout)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topStories = Dummy.stories
        setUpTopStoriesCarousel(topStories)
        setUpStoriesList(topStories)
    }


    private fun getViews() {
        binding.let {
            toolbar = it.topAppBar
            drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
            viewPagerTopStories = it.viewPageTopStories
            rvTopStoriesCarouselState = it.recyclerViewState
            rvStories = it.rvStories
        }
    }

    private fun setUpTopStoriesCarousel(topStories: List<Story>) {
        topStoriesCarouselItemsAdapter = TopStoriesViewPagerAdapter(requireActivity(), topStories)
        viewPagerTopStories.setPageTransformer(MarginPageTransformer(28))
        viewPagerTopStories.adapter = topStoriesCarouselItemsAdapter
        viewPagerTopStories.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                topStoriesCarouselStateAdapter.updateList(position)
            }
        })

        val stateList =
            TopStoriesStateAdapter.initStateList(topStories.size)
        topStoriesCarouselStateAdapter = TopStoriesStateAdapter(stateList)
        rvTopStoriesCarouselState.setHasFixedSize(true)
        rvTopStoriesCarouselState.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTopStoriesCarouselState.adapter = topStoriesCarouselStateAdapter

    }

    private fun setUpStoriesList(stories: List<Story>) {
        storiesListAdapter = StoriesListAdapter {
            Log.d("TEST", "${it.id}")
        }
        rvStories.layoutManager = LinearLayoutManager(requireContext())
        rvStories.adapter = storiesListAdapter
        storiesListAdapter.submitList(stories)
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