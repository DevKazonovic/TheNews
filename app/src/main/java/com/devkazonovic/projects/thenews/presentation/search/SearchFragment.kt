package com.devkazonovic.projects.thenews.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.FragmentSearchBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel
            by hiltNavGraphViewModels<SearchViewModel>(R.id.searchPage)

    private lateinit var navController: NavController
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
        navController = findNavController()
        initViews()
        toolbar.setMainPageToolbar(navController, drawerLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStoriesList()
        tfSearch.editText?.doOnTextChanged { text, _, _, _ ->
            Timber.d(text.toString())
            viewModel.setSearchKeyWord(text?.trim().toString())
            viewModel.search()
        }

        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    storiesListAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    storiesListAdapter.submitList(listOf())
                }
                is Resource.Loading -> {
                    storiesListAdapter.submitList(listOf())
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
            toolbar = it.topAppBar
            rvSearchStory = it.rvSearchStories
            tfSearch = it.tfSearch
        }
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
    }

    private fun initStoriesList() {
        storiesListAdapter = StoriesListAdapter(requireContext(),articleScrapper, {
            Timber.d(it.url)
        }, {})
        rvSearchStory.layoutManager = LinearLayoutManager(requireContext())
        rvSearchStory.adapter = storiesListAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}