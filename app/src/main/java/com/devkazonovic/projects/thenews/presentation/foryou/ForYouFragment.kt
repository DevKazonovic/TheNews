package com.devkazonovic.projects.thenews.presentation.foryou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.common.extensions.show
import com.devkazonovic.projects.thenews.common.util.IntentUtil
import com.devkazonovic.projects.thenews.common.util.ViewUtil.hide
import com.devkazonovic.projects.thenews.common.util.ViewUtil.show
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.FragmentForyouBinding
import com.devkazonovic.projects.thenews.databinding.LayoutErrorBinding
import com.devkazonovic.projects.thenews.databinding.LayoutLoadingBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import com.devkazonovic.projects.thenews.presentation.common.storymenu.StoryMenuFragment
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.CarouselAdapter
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.CarouselStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForYouFragment : Fragment() {

    private val viewModel by
    hiltNavGraphViewModels<ForYouViewModel>(R.id.forYouPage)

    @Inject
    lateinit var articleScrapper: ArticleScrapper

    private var _binding: FragmentForyouBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutData: ViewGroup
    private lateinit var layoutLoad: LayoutLoadingBinding
    private lateinit var layoutError: LayoutErrorBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPagerTopStories: ViewPager2
    private lateinit var rvTopStoriesCarouselState: RecyclerView
    private lateinit var rvStories: RecyclerView

    private lateinit var storiesListAdapter: StoriesListAdapter
    private lateinit var topStoriesCarouselItemsAdapter: CarouselAdapter
    private lateinit var carouselCarouselStateAdapter: CarouselStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForyouBinding.inflate(inflater, container, false)
        initViews()
        toolbar.setMainPageToolbar(findNavController(), drawerLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpStoriesRecyclerView()
        setUpTopFiveStoriesCarousel()

        viewModel.stories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Success -> {
                    onSuccess(it.data)
                }
                is Resource.Error -> {
                    onError(getString(it.messageId))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        storiesListAdapter.dispose.clear()
        _binding = null
    }

    private fun initViews() {
        binding.let {
            layoutData = it.layoutData
            layoutLoad = it.layoutLoad
            layoutError = it.layoutError
            toolbar = it.topAppBar
            drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
            viewPagerTopStories = it.viewPageTopStories
            rvTopStoriesCarouselState = it.recyclerViewState
            rvStories = it.rvStories
        }

        binding.refreshLayout.setOnRefreshListener {
            onRefresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun setUpTopFiveStoriesCarousel() {
        viewPagerTopStories.setPageTransformer(MarginPageTransformer(28))
        viewPagerTopStories.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                carouselCarouselStateAdapter.updateList(position)
            }
        })
        val stateList =
            CarouselStateAdapter.initStateList(5)
        carouselCarouselStateAdapter = CarouselStateAdapter(stateList)
        rvTopStoriesCarouselState.layoutManager =
            LinearLayoutManager(requireContext(), HORIZONTAL, false)
        rvTopStoriesCarouselState.adapter = carouselCarouselStateAdapter

    }

    private fun setUpStoriesRecyclerView() {
        storiesListAdapter = StoriesListAdapter(this, articleScrapper, {
            onStoryClick(it)
        }, {
            onStoryMenuClick(it)
        })
        rvStories.layoutManager = LinearLayoutManager(requireContext())
        rvStories.adapter = storiesListAdapter
    }

    private fun onStoryMenuClick(story: Story) {
        StoryMenuFragment.newInstance(story).show(childFragmentManager, StoryMenuFragment.TAG)
    }

    private fun onStoryClick(it: Story) {
        IntentUtil.openUrl(requireContext(), it.url)
    }

    private fun onRefresh() {
        viewModel.loadData(reload = true, clearCache = false)
    }

    private fun onLoading() {
        hide(layoutData)
        layoutError.hide()
        layoutLoad.show()
    }

    private fun onSuccess(stories: List<Story>?) {
        hide(layoutError, layoutLoad)
        show(layoutData)
        stories?.let {
            if (it.isNotEmpty()) {
                topStoriesCarouselItemsAdapter =
                    CarouselAdapter(this, it.subList(0, 5))
                viewPagerTopStories.adapter = topStoriesCarouselItemsAdapter
                storiesListAdapter.submitList(it.subList(6, it.size))
            }
        }
    }

    private fun onError(description: String) {
        hide(layoutData)
        hide(layoutLoad)
        layoutError.show()
        layoutError.txTitle.text = getString(R.string.errors_title)
        layoutError.txSubtitle.text = description
        layoutError.btnActionError.setOnClickListener {
            onRefresh()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForYouFragment()
        const val TAG = "ForYou Page"
    }
}