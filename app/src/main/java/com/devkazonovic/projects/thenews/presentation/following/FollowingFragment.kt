package com.devkazonovic.projects.thenews.presentation.following

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
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.setMainPageToolbar
import com.devkazonovic.projects.thenews.common.extensions.show
import com.devkazonovic.projects.thenews.common.util.ViewUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.FragmentFollowingBinding
import com.devkazonovic.projects.thenews.databinding.LayoutErrorBinding
import com.devkazonovic.projects.thenews.databinding.LayoutLoadingBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val viewModel
            by hiltNavGraphViewModels<FollowingViewModel>(R.id.followingPage)

    @Inject
    lateinit var articleScrapper: ArticleScrapper

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutData: ViewGroup
    private lateinit var layoutLoad: LayoutLoadingBinding
    private lateinit var layoutError: LayoutErrorBinding
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var rvSavedStories: RecyclerView

    private lateinit var storiesListAdapter: StoriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        navController = findNavController()
        initViews()
        toolbar.setMainPageToolbar(navController, drawerLayout)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.load()
        viewModel.savedStories.observe(viewLifecycleOwner) {
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
            toolbar = it.topAppBar
            layoutData = it.layoutData
            layoutLoad = it.layoutLoad
            layoutError = it.layoutError
            rvSavedStories = it.rvSavedStories
            drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        }
    }

    private fun setUpRecyclerView() {
        storiesListAdapter = StoriesListAdapter(requireContext(),articleScrapper, {

        }, {

        })

        rvSavedStories.layoutManager = LinearLayoutManager(requireContext())
        rvSavedStories.adapter = storiesListAdapter
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


    companion object {

        @JvmStatic
        fun newInstance() = FollowingFragment()
    }
}