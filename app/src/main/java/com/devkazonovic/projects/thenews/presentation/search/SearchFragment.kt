package com.devkazonovic.projects.thenews.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.common.extensions.show
import com.devkazonovic.projects.thenews.common.util.IntentUtil
import com.devkazonovic.projects.thenews.common.util.ViewUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.FragmentSearchBinding
import com.devkazonovic.projects.thenews.databinding.LayoutErrorBinding
import com.devkazonovic.projects.thenews.databinding.LayoutLoadingBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import com.devkazonovic.projects.thenews.presentation.common.storymenu.StoryMenuFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel
            by hiltNavGraphViewModels<SearchViewModel>(R.id.searchPage)

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutData: ViewGroup
    private lateinit var layoutLoad: LayoutLoadingBinding
    private lateinit var layoutError: LayoutErrorBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var rvSearchStory: RecyclerView
    private lateinit var tfSearch: TextInputLayout
    private lateinit var storiesListAdapter: StoriesListAdapter

    @Inject
    lateinit var articleScrapper: ArticleScrapper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViews()
        toolbar.setMainPageToolbar(findNavController(), drawerLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpStoriesRecyclerView()
        tfSearch.editText!!.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setSearchKeyWord(tfSearch.editText!!.text.toString().trim())
                viewModel.search()
                true
            } else {
                false
            }
        }
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    onSuccess(it.data)
                }
                is Resource.Error -> {
                    onError(getString(it.messageId))
                }
                is Resource.Loading -> {
                    onLoading()
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
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        binding.let {
            toolbar = it.topAppBar
            layoutData = it.layoutData
            layoutLoad = it.layoutLoad
            layoutError = it.layoutError
            rvSearchStory = it.rvSearchStories
            tfSearch = it.tfSearch
        }

        layoutError.btnActionError.setOnClickListener {
            viewModel.search()
        }
    }

    private fun setUpStoriesRecyclerView() {
        storiesListAdapter = StoriesListAdapter(this, articleScrapper, {
            onStoryClick(it)
        }, {
            onStoryMenuClick(it)
        })
        rvSearchStory.layoutManager = LinearLayoutManager(requireContext())
        rvSearchStory.adapter = storiesListAdapter

    }

    private fun onLoading() {
        ViewUtil.hide(layoutData)
        layoutError.hide()
        layoutLoad.show()
    }

    private fun onSuccess(stories: List<Story>?) {
        ViewUtil.hide(layoutError, layoutLoad)
        ViewUtil.show(layoutData)
        stories?.let {
            if (it.isNotEmpty()) {
                storiesListAdapter.submitList(it)
            }
        }
    }

    private fun onError(description: String) {
        ViewUtil.hide(layoutData)
        ViewUtil.hide(layoutLoad)
        layoutError.show()
        layoutError.txTitle.text = getString(R.string.errors_title)
        layoutError.txSubtitle.text = description
    }

    private fun onStoryMenuClick(story: Story) {
        StoryMenuFragment.newInstance(story).show(childFragmentManager, StoryMenuFragment.TAG)
    }

    private fun onStoryClick(it: Story) {
        IntentUtil.openUrl(requireContext(), it.url)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}