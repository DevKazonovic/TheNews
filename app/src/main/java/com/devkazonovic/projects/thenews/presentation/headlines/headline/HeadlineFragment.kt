package com.devkazonovic.projects.thenews.presentation.headlines.headline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.extensions.hide
import com.devkazonovic.projects.thenews.common.extensions.show
import com.devkazonovic.projects.thenews.common.util.ViewUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.FragmentHeadlineBinding
import com.devkazonovic.projects.thenews.databinding.LayoutErrorBinding
import com.devkazonovic.projects.thenews.databinding.LayoutLoadingBinding
import com.devkazonovic.projects.thenews.domain.model.Resource
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.StoriesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY_TOPIC_ID = "Topic Id"

@AndroidEntryPoint
class HeadlineFragment : Fragment() {

    private lateinit var topicId: String

    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutData: ViewGroup
    private lateinit var layoutLoad: LayoutLoadingBinding
    private lateinit var layoutError: LayoutErrorBinding
    private lateinit var rvHeadlines: RecyclerView
    private lateinit var adapter: StoriesListAdapter

    @Inject
    lateinit var articleScrapper: ArticleScrapper
    private val viewModel: HeadlineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topicId = it.getString(KEY_TOPIC_ID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        viewModel.setTopicId(topicId)
        viewModel.load()
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
            rvHeadlines = it.rvHeadlines
        }
    }

    private fun setUpList() {
        rvHeadlines.layoutManager = LinearLayoutManager(requireContext())
        adapter = StoriesListAdapter(requireContext(),articleScrapper, {
        }, {})
        rvHeadlines.adapter = adapter
    }

    private fun onLoading() {
        ViewUtil.hide(layoutData)
        layoutError.hide()
        layoutLoad.show()
    }

    private fun onSuccess(stories: List<Story>) {
        ViewUtil.hide(layoutError, layoutLoad)
        ViewUtil.show(layoutData)
        stories.let {
            adapter.submitList(it)
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
        fun newInstance(topicId: String) = HeadlineFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TOPIC_ID, topicId)
            }
        }

    }

}