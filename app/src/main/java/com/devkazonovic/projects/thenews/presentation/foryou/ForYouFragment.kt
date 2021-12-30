package com.devkazonovic.projects.thenews.presentation.foryou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.common.extensions.show
import com.devkazonovic.projects.thenews.common.util.ViewUtil.hide
import com.devkazonovic.projects.thenews.common.util.ViewUtil.show
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.ErrorLayoutBinding
import com.devkazonovic.projects.thenews.databinding.FragmentForyouBinding
import com.devkazonovic.projects.thenews.databinding.LoadLayoutBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.TopStoriesStateAdapter
import com.devkazonovic.projects.thenews.presentation.foryou.topstories.TopStoriesViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ForYouFragment : Fragment() {

    private var _binding: FragmentForyouBinding? = null
    private val binding get() = _binding!!

    private val viewModel by
    hiltNavGraphViewModels<ForYouViewModel>(R.id.forYouPage)

    @Inject
    lateinit var articleScrapper: ArticleScrapper

    private lateinit var layoutData: ViewGroup
    private lateinit var layoutLoad: LoadLayoutBinding
    private lateinit var layoutError: ErrorLayoutBinding

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
        initViews()
        navController = findNavController()
        toolbar.setMainPageToolbar(navController, drawerLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.loadData()
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
    }

    private fun setUpRecyclerView() {
        storiesListAdapter = StoriesListAdapter(articleScrapper,{
            Timber.d(it.url)
        },{

        })
        rvStories.layoutManager = LinearLayoutManager(requireContext())
        rvStories.adapter = storiesListAdapter
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
                setUpTopStoriesCarousel(it.subList(0, 5))
                setUpStoriesList(it.subList(6, it.size))
            }
        }
    }

    private fun onError(description: String) {
        hide(layoutData)
        hide(layoutLoad)
        layoutError.show()
        layoutError.txTitle.text = getString(R.string.errors_title)
        layoutError.txSubtitle.text = description
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
        rvTopStoriesCarouselState.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTopStoriesCarouselState.adapter = topStoriesCarouselStateAdapter

    }

    private fun setUpStoriesList(stories: List<Story>) {
        storiesListAdapter.submitList(stories)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForYouFragment()
    }
}