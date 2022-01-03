package com.devkazonovic.projects.thenews.presentation.common.storymenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.databinding.LayoutMenuStoryBinding
import com.devkazonovic.projects.thenews.domain.model.Story
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

private const val KEY_STORY = "Story"

@AndroidEntryPoint
class StoryMenuFragment : BottomSheetDialogFragment() {

    private var story: Story? = null

    private val viewModel by
    viewModels<StoryMenuViewModel>({ requireParentFragment() })

    private var _binding: LayoutMenuStoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemSaveStory: ViewGroup
    private lateinit var itemShareStory: ViewGroup
    private lateinit var itemOpenSource: ViewGroup

    private lateinit var imageViewSaveStory: ImageView
    private lateinit var imageViewShareStory: ImageView
    private lateinit var imageViewOpenSource: ImageView

    private lateinit var textViewSaveStory: TextView
    private lateinit var textViewShareStory: TextView
    private lateinit var textViewOpenSource: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            story = it?.getSerializable(KEY_STORY) as Story
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutMenuStoryBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStory(story)

        viewModel.isActionCompleted.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) dismiss()
            }
        }
    }

    private fun initViews() {
        binding.let {
            itemSaveStory = binding.itemSaveStory
            itemShareStory = binding.itemShareStory
            itemOpenSource = binding.itemOpenSource
            imageViewSaveStory = binding.imageViewSaveStory
            imageViewShareStory = binding.imageViewShareStory
            imageViewOpenSource = binding.imageViewOpenSource
            textViewSaveStory = binding.textViewSaveStory
            textViewShareStory = binding.textViewShareStory
            textViewOpenSource = binding.textViewOpenSource
        }

        story?.let {
            if (story!!.isSaved) {
                textViewSaveStory.text = getString(R.string.label_remove_story)
                imageViewSaveStory.setImageResource(R.drawable.ic_bookmarked)
            } else {
                textViewSaveStory.text = getString(R.string.label_save_for_later)
                imageViewSaveStory.setImageResource(R.drawable.ic_bookmark)
            }
        }

        itemSaveStory.setOnClickListener {
            if (story!!.isSaved) viewModel.removeStoryFromReadLater()
            else viewModel.saveStoryToReadLater()

            dismiss()
        }
        itemShareStory.setOnClickListener {

        }

        itemOpenSource.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val TAG = "Story Menu Fragment"

        @JvmStatic
        fun newInstance(story: Story) = StoryMenuFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_STORY, story)
            }
        }

    }
}